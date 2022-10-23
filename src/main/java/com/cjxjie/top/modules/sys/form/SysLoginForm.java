package com.cjxjie.top.modules.sys.form;

import lombok.Data;
import lombok.ToString;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/08 16:04
 */
@Data
@ToString
public class SysLoginForm {
    private String username;
    private String password;
    private String captcha;
    private String uuid;
}
