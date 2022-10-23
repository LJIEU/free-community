package com.cjxjie.top.modules.app.controller.api;

import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/06 11:13
 */
@Api(tags = "用户信息表")
@RestController
@RequestMapping("app/api/user")
public class UserApiController {
    @Lazy
    @Autowired
    private UserService userService;

    /**
     * 根据 用户名 获取用户信息
     */
    @GetMapping("/userInfo/{name}")
    public R getUserByName(@PathVariable(value = "name") String name) {
        UserEntity user = userService.getByUsername(name);
        return R.ok().put("userInfo", user);
    }
}
