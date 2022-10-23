package com.cjxjie.top.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import java.io.Serial;

/**
 * 帖子和分类关联表
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-18 14:47:45
 */
@Data
@TableName("invitation_category")
public class InvitationCategoryEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * ID 
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 帖子ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long invitationId;
	/**
	 * 分类ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long categoryId;

}
