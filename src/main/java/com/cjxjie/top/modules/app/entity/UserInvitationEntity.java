package com.cjxjie.top.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import java.io.Serial;

/**
 * 用户与帖子关联表
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-18 14:47:45
 */
@Data
@TableName("user_invitation")
public class UserInvitationEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 用户ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 帖子ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long invitationId;

}
