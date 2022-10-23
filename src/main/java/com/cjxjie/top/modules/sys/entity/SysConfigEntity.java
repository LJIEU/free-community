package com.cjxjie.top.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 系统配置信息表
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-06 17:36:34
 */
@Data
@TableName("sys_config")
public class SysConfigEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId
	private Long id;
	/**
	 * key
	 */
	private String paramKey;
	/**
	 * value
	 */
	private String paramValue;
	/**
	 * 状态   0：隐藏   1：显示
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;

}
