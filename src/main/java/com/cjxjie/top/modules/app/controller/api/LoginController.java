package com.cjxjie.top.modules.app.controller.api;

import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.app.form.LoginForm;
import com.cjxjie.top.modules.app.service.CodeService;
import com.cjxjie.top.modules.app.service.UserService;
import com.cjxjie.top.modules.app.utils.JwtTokenUtil;
import com.cjxjie.top.modules.app.vo.RegisterVo;
import com.cjxjie.top.modules.app.vo.VerifyVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/18 8:35
 */
@Slf4j
@RestController
@RequestMapping("app/api")
@SuppressWarnings("all")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CodeService codeService;

    /**
     * 注册用户
     */
    @PostMapping("/register")
    public R Register(@RequestBody RegisterVo registerVo) {
        // 先验证验证码是否正确
        Boolean aBoolean = codeService.verifiCode(registerVo.getCode(), registerVo.getPhone());
        if (aBoolean) {
            userService.Register(registerVo);
            return R.ok();
        }
        return R.error().message("验证码错误!");
    }

    /**
     * 使用手机号获取验证码
     */
    @GetMapping("/getVerifyCode/{phone}")
    public void getVerifiCode(@PathVariable(value = "phone") String phone, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store,no-cache");
        response.setContentType("image/jpeg");

        // 向对应的手机发送验证码
        BufferedImage image = userService.getVerifiCode(phone);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
//        return R.ok();
    }


    @PostMapping("/login")
    public R login(@RequestBody LoginForm loginForm) {
//        log.warn(loginForm.toString());
        return userService.login(loginForm.getUsername(), loginForm.getPassword());
    }

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * 验证登录用户的jwtToken信息是否失效
     *
     * @param verifyVo
     * @return
     */
    @PostMapping("/verify")
    public R verify(@RequestBody VerifyVo verifyVo) {
//        String jwt_token = request.getHeader("jwt_token");
//        String username = request.getHeader("username");
        String username = verifyVo.getUsername();
        String jwtToken = verifyVo.getJwtToken();
        log.warn("用户信息:" + username + "jwtToken信息:" + jwtToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtToken != null) {
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
//                // 刷新 token
//                jwt_token = jwtTokenUtil.refreshToken(jwt_token);
                return R.ok().put("jwt_token", jwtToken);
            } else {
                return R.error(403, "重新登录Token无效!");
            }
        }
        return R.error();
    }

}
