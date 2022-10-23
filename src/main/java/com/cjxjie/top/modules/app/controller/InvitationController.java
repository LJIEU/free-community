package com.cjxjie.top.modules.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cjxjie.top.modules.app.vo.UserInvitationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.cjxjie.top.modules.app.service.InvitationService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;


/**
 * 帖子信息表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@Api(tags = "帖子信息表")
@RestController
@RequestMapping("app/invitation")
public class InvitationController {
    @Autowired
    private InvitationService invitationService;

    /**
     * 列表
     */
    @ApiOperation("获取所有帖子信息表列表")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = invitationService.queryPage(params);
        // 重新处理数据
        List<UserInvitationVo> list = invitationService.getUserAndPost(page.getList());

        // 插入新数据
        page.setList(list);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("根据invitationId获取信息")
    @GetMapping("/info/{invitationId}")
    @RequiresPermissions("app:invitation:all")
    public R info(@PathVariable("invitationId") Long invitationId) {
        InvitationEntity invitation = invitationService.getById(invitationId);

        return R.ok().put("invitation", invitation);
    }

    /**
     * 保存 【后台管理员进行保存】
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @RequiresPermissions("app:invitation:all")
    public R save(@RequestBody InvitationEntity invitation) {
        invitationService.save(invitation);
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @RequiresPermissions("app:invitation:all")
    public R update(@RequestBody InvitationEntity invitation) {
        invitationService.updateById(invitation);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("app:invitation:all")
    public R delete(@RequestBody Long[] invitationIds) {
        invitationService.removeByIds(Arrays.asList(invitationIds));

        return R.ok();
    }

}
