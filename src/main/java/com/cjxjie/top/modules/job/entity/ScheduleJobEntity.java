package com.cjxjie.top.modules.job.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 定时任务
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-11 15:11:54
 */
@Data
@TableName("schedule_job")
public class ScheduleJobEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 任务id
	 */
	@TableId
	private Long jobId;
	/**
	 * spring bean名称
	 */
	private String beanName;
	/**
	 * 参数
	 */
	private String params;
	/**
	 * cron表达式
	 */
	private String cronExpression;
	/**
	 * 任务状态  0：正常  1：暂停
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

}
