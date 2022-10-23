package com.cjxjie.top.modules.app.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/22 16:30
 */
@Data
@ToString
public class RegisterVo {
    private String username;
    private String password;
    private String phone;
    private String code;
}
