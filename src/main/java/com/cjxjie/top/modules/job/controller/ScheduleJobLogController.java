package com.cjxjie.top.modules.job.controller;

import java.util.Arrays;
import java.util.Map;

import com.cjxjie.top.modules.job.entity.ScheduleJobLogEntity;
import com.cjxjie.top.modules.job.service.ScheduleJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;


/**
 * 定时任务日志
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-11 15:11:54
 */
@Api(tags = "定时任务日志")
@RestController
@RequestMapping("sys/schedulejoblog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 列表
     */
    @ApiOperation("获取所有定时任务日志列表")
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedulejoblog:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = scheduleJobLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("根据logId获取信息")
    @RequestMapping("/info/{logId}")
    @RequiresPermissions("sys:schedulejoblog:info")
    public R info(@PathVariable("logId") Long logId) {
            ScheduleJobLogEntity scheduleJobLog = scheduleJobLogService.getById(logId);

        return R.ok().put("scheduleJobLog", scheduleJobLog);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedulejoblog:save")
    public R save(@RequestBody ScheduleJobLogEntity scheduleJobLog) {
            scheduleJobLogService.save(scheduleJobLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedulejoblog:update")
    public R update(@RequestBody ScheduleJobLogEntity scheduleJobLog) {
            scheduleJobLogService.updateById(scheduleJobLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedulejoblog:delete")
    public R delete(@RequestBody Long[] logIds) {
            scheduleJobLogService.removeByIds(Arrays.asList(logIds));

        return R.ok();
    }

}
