package com.cjxjie.top.modules.job.service.impl;

import com.cjxjie.top.modules.job.dao.ScheduleJobLogDao;
import com.cjxjie.top.modules.job.entity.ScheduleJobLogEntity;
import com.cjxjie.top.modules.job.service.ScheduleJobLogService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;



@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ScheduleJobLogEntity> page = this.page(
                new Query<ScheduleJobLogEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}