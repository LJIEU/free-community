package com.cjxjie.top.modules.sys.controller;

import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjxjie.top.modules.sys.service.SysLogService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;


/**
 * 系统日志
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Api(tags = "系统日志")
@RestController
@RequestMapping("sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @ApiOperation("获取所有系统日志列表")
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysLogService.queryPage(params);

        return R.ok().put("page", page);
    }
}
