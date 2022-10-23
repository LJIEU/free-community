package com.cjxjie.top.modules.sys.controller;

import java.util.Map;

import com.cjxjie.top.common.annotaion.SysLog;
import com.cjxjie.top.common.validator.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cjxjie.top.modules.sys.entity.SysConfigEntity;
import com.cjxjie.top.modules.sys.service.SysConfigService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;


/**
 * 系统配置信息表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Api(tags = "系统配置信息表")
@RestController
@RequestMapping("sys/config")
public class SysConfigController extends AbstractController{
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 列表
     */
    @ApiOperation("获取所有系统配置信息表列表")
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysConfigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 配置信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public R info(@PathVariable("id") Long id){
        SysConfigEntity config = sysConfigService.getById(id);

        return R.ok().put("config", config);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @PostMapping("/save")
    @RequiresPermissions("sys:config:save")
    public R save(@RequestBody SysConfigEntity config){
        ValidatorUtils.validateEntity(config);

        sysConfigService.saveConfig(config);

        return R.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @PostMapping("/update")
    @RequiresPermissions("sys:config:update")
    public R update(@RequestBody SysConfigEntity config){
        ValidatorUtils.validateEntity(config);

        sysConfigService.update(config);

        return R.ok();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @PostMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public R delete(@RequestBody Long[] ids){
        sysConfigService.deleteBatch(ids);

        return R.ok();
    }
}
