package com.cjxjie.top.modules.job.service.impl;

import com.cjxjie.top.modules.job.dao.ScheduleJobDao;
import com.cjxjie.top.modules.job.entity.ScheduleJobEntity;
import com.cjxjie.top.modules.job.service.ScheduleJobService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;



@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobDao, ScheduleJobEntity> implements ScheduleJobService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ScheduleJobEntity> page = this.page(
                new Query<ScheduleJobEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}