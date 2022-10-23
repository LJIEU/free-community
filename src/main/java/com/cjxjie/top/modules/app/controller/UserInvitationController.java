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

import com.cjxjie.top.modules.app.entity.UserInvitationEntity;
import com.cjxjie.top.modules.app.service.UserInvitationService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;


/**
 * 用户与帖子关联表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@Api(tags = "用户与帖子关联表")
@RestController
@RequestMapping("app/userinvitation")
public class UserInvitationController {
    @Autowired
    private UserInvitationService userInvitationService;

    /**
     * 列表
     */
    @ApiOperation("获取所有用户与帖子关联表列表")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userInvitationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("根据id获取信息")
    @RequestMapping("/info/{id}")
    @RequiresPermissions("app:userinvitation:info")
    public R info(@PathVariable("id") Long id) {
            UserInvitationEntity userInvitation = userInvitationService.getById(id);

        return R.ok().put("userInvitation", userInvitation);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping("/save")
    @RequiresPermissions("app:userinvitation:save")
    public R save(@RequestBody UserInvitationEntity userInvitation) {
            userInvitationService.save(userInvitation);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping("/update")
    @RequiresPermissions("app:userinvitation:update")
    public R update(@RequestBody UserInvitationEntity userInvitation) {
            userInvitationService.updateById(userInvitation);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("app:userinvitation:delete")
    public R delete(@RequestBody Long[] ids) {
            userInvitationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
