package com.cjxjie.top.modules.sys.controller;

import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpStatus;
import com.cjxjie.top.common.annotaion.SysLog;
import com.cjxjie.top.common.utils.Constant;
import com.cjxjie.top.common.validator.Assert;
import com.cjxjie.top.common.validator.ValidatorUtils;
import com.cjxjie.top.common.validator.group.AddGroup;
import com.cjxjie.top.common.validator.group.UpdateGroup;
import com.cjxjie.top.modules.sys.form.PasswordForm;
import com.cjxjie.top.modules.sys.service.SysUserRoleService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cjxjie.top.modules.sys.entity.SysUserEntity;
import com.cjxjie.top.modules.sys.service.SysUserService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;


/**
 * 系统用户
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Api(tags = "系统用户")
@RestController
@RequestMapping("sys/user")
public class SysUserController extends AbstractController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 所有用户列表【当前用户的创建的用户列表】
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
//        log.warn("当前用户ID:" + String.valueOf(getUserId()));
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");
//        log.warn(form.getPassword() + "\t新:" + form.getNewPassword());
        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.error(HttpStatus.HTTP_BAD_GATEWAY, "原密码不正确");
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
//        log.warn(user.toString());
        user.setCreateUserId(getUserId());
        if (!sysUserService.saveUser(user)) {
            return R.error(HttpStatus.HTTP_INTERNAL_ERROR, "保存失败!");
        }
        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        user.setCreateUserId(getUserId());
        log.warn(user + "\t创建者ID:" + getUserId());
        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error(HttpStatus.HTTP_INTERNAL_ERROR, "系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error(HttpStatus.HTTP_INTERNAL_ERROR, "当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return R.ok();
    }
}
