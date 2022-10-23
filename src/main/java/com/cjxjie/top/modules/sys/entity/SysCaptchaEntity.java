package com.cjxjie.top.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统验证码
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-06 17:36:34
 */
@Data
@TableName("sys_captcha")
public class SysCaptchaEntity implements Serializable {
	@Serial
    private static final long serialVersionUID = 1L;

	/**
	 * uuid
	 */
	@TableId
	private String uuid;
	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 过期时间
	 */
	private Date expireTime;

}
