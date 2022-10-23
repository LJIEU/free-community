package com.cjxjie.top.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjxjie.top.modules.app.entity.InvitationCategoryEntity;
import com.cjxjie.top.modules.app.service.InvitationCategoryService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;


/**
 * 帖子和分类关联表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@Api(tags = "帖子和分类关联表")
@RestController
@RequestMapping("app/invitationcategory")
public class InvitationCategoryController {
    @Autowired
    private InvitationCategoryService invitationCategoryService;

    /**
     * 列表
     */
    @ApiOperation("获取所有帖子和分类关联表列表")
    @RequestMapping("/list")
    @RequiresPermissions("app:invitationcategory:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = invitationCategoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("根据id获取信息")
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:invitationcategory:info")
    public R info(@PathVariable("id") Long id) {
            InvitationCategoryEntity invitationCategory = invitationCategoryService.getById(id);

        return R.ok().put("invitationCategory", invitationCategory);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping("/save")
    @RequiresPermissions("app:invitationcategory:save")
    public R save(@RequestBody InvitationCategoryEntity invitationCategory) {
            invitationCategoryService.save(invitationCategory);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping("/update")
    @RequiresPermissions("app:invitationcategory:update")
    public R update(@RequestBody InvitationCategoryEntity invitationCategory) {
            invitationCategoryService.updateById(invitationCategory);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("app:invitationcategory:delete")
    public R delete(@RequestBody Long[] ids) {
            invitationCategoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
