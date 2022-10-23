package com.cjxjie.top.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 系统日志
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-06 17:36:34
 */
@Data
@TableName("sys_log")
public class SysLogEntity implements Serializable {
	@Serial
    private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户操作
	 */
	private String operation;
	/**
	 * 请求方法
	 */
	private String method;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * 执行时长(毫秒)
	 */
	private Long time;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
