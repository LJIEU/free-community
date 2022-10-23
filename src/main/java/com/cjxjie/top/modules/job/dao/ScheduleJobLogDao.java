package com.cjxjie.top.modules.job.dao;

import com.cjxjie.top.modules.job.entity.ScheduleJobLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-11 15:11:54
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {
	
}
