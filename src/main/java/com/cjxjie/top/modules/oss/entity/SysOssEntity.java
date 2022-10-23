package com.cjxjie.top.modules.oss.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文件上传
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-11 15:10:37
 */
@Data
@TableName("sys_oss")
public class SysOssEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * URL地址
	 */
	private String url;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
