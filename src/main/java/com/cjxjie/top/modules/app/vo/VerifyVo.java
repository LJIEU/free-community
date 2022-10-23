package com.cjxjie.top.modules.app.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/22 11:16
 */
@Data
@ToString
public class VerifyVo {
    private String jwtToken;
    private String username;
}
