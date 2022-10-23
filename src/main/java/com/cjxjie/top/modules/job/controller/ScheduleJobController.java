package com.cjxjie.top.modules.job.controller;

import java.util.Arrays;
import java.util.Map;

import com.cjxjie.top.modules.job.entity.ScheduleJobEntity;
import com.cjxjie.top.modules.job.service.ScheduleJobService;
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
 * 定时任务
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-11 15:11:54
 */
@Api(tags = "定时任务")
@RestController
@RequestMapping("sys/schedulejob")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 列表
     */
    @ApiOperation("获取所有定时任务列表")
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedulejob:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = scheduleJobService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("根据jobId获取信息")
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedulejob:info")
    public R info(@PathVariable("jobId") Long jobId) {
            ScheduleJobEntity scheduleJob = scheduleJobService.getById(jobId);

        return R.ok().put("scheduleJob", scheduleJob);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedulejob:save")
    public R save(@RequestBody ScheduleJobEntity scheduleJob) {
            scheduleJobService.save(scheduleJob);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedulejob:update")
    public R update(@RequestBody ScheduleJobEntity scheduleJob) {
            scheduleJobService.updateById(scheduleJob);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedulejob:delete")
    public R delete(@RequestBody Long[] jobIds) {
            scheduleJobService.removeByIds(Arrays.asList(jobIds));

        return R.ok();
    }

}
