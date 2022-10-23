package com.cjxjie.top.modules.app.vo;

import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/05 17:24
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class UserInvitationVo extends InvitationEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 职业
     */
    private String profession;


    private String documentTranslate;
}
