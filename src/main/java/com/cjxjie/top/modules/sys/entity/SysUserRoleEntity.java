package com.cjxjie.top.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 用户与角色对应关系
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-06 17:36:34
 */
@Data
@TableName("sys_user_role")
public class SysUserRoleEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
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
	 * 角色ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long roleId;

}
