package com.cjxjie.top.modules.sys.controller;

import cn.hutool.http.HttpStatus;
import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.sys.entity.SysUserEntity;
import com.cjxjie.top.modules.sys.form.SysLoginForm;
import com.cjxjie.top.modules.sys.service.SysCaptchaService;
import com.cjxjie.top.modules.sys.service.SysUserService;
import com.cjxjie.top.modules.sys.service.SysUserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/08 15:14
 * 系统用户登录
 */
@Slf4j
@RestController
public class SysLoginController extends AbstractController {

    @Autowired
    private SysCaptchaService sysCaptchaService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 生成验证码
     */
    @GetMapping("verification-code.jpg")
    public void verificationCode(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store,no-cache");
        response.setContentType("image/jpeg");
//        log.warn(uuid);
        // 获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @PostMapping("/sys/login")
    public R login(@RequestBody SysLoginForm form) {
        log.warn(form.toString());
        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if (!captcha) {
            return R.error(HttpStatus.HTTP_BAD_GATEWAY, "验证码错误!");
        }
        SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return R.error(HttpStatus.HTTP_BAD_GATEWAY, "账号或密码不正确!");
        }

        if (user.getStatus() == 0) {
            return R.error(HttpStatus.HTTP_BAD_GATEWAY, "账号已被锁定,请联系管理员");
        }

        // 生成Token
        R r = sysUserTokenService.createToken(user.getUserId());
        return r;
    }

    /**
     * 退出
     */
    @PostMapping("sys/logout")
    public R logout() {
        sysUserTokenService.logout(getUserId());
        return R.ok(HttpStatus.HTTP_OK, "退出成功");
    }
}
