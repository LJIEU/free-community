package com.cjxjie.top.modules.sys.form;

import lombok.Data;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/08 16:56
 */
@Data
public class PasswordForm {
    /**
     * 原密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;
}
