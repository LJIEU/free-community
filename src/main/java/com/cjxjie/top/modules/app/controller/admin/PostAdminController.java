package com.cjxjie.top.modules.app.controller.admin;

import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.cjxjie.top.modules.app.service.InvitationService;
import com.cjxjie.top.modules.app.vo.UserInvitationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/10 14:13
 */
@Log4j
@Api(tags = "帖子信息表")
@RestController
@RequestMapping("app/admin/invitation")
public class PostAdminController {
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
