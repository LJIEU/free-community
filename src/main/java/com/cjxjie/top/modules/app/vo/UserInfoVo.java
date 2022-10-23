package com.cjxjie.top.modules.app.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * The type User info vo.
 *
 * @author 刘杰
 * @version 1.0
 * @since 2022 /10/15 11:15
 * 用户基本信息
 */
@Data
public class UserInfoVo {
    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户头像
     */
    private String avatarURL;

    /**
     * 用户主页
     */
    private String homeURL;
}
