package com.cjxjie.top.modules.sys.controller;

import java.util.Arrays;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cjxjie.top.modules.sys.entity.SysCaptchaEntity;
import com.cjxjie.top.modules.sys.service.SysCaptchaService;
import com.cjxjie.top.common.utils.R;


/**
 * 系统验证码
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Api(tags = "系统验证码")
@RestController
@RequestMapping("sys/captcha")
public class SysCaptchaController {
    @Autowired
    private SysCaptchaService sysCaptchaService;


    /**
     * 信息
     */
    @ApiOperation("根据uuid获取信息")
    @GetMapping("/info/{uuid}")
    @RequiresPermissions("sys:captcha:info")
    public R info(@PathVariable("uuid") String uuid) {
            SysCaptchaEntity captcha = sysCaptchaService.getById(uuid);

        return R.ok().put("captcha", captcha);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:captcha:save")
    public R save(@RequestBody SysCaptchaEntity captcha) {
            sysCaptchaService.save(captcha);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PutMapping("/update")
    @RequiresPermissions("sys:captcha:update")
    public R update(@RequestBody SysCaptchaEntity captcha) {
            sysCaptchaService.updateById(captcha);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:captcha:delete")
    public R delete(@RequestBody String[] uuids) {
            sysCaptchaService.removeByIds(Arrays.asList(uuids));

        return R.ok();
    }

}
