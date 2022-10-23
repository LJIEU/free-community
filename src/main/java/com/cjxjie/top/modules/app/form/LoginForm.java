package com.cjxjie.top.modules.app.form;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/18 15:10
 */
@Data
@ToString
public class LoginForm {

    @NotBlank(message = "用户名不能为空")
    private String username;


    @NotBlank(message = "密码不能为空")
    private String password;
}
