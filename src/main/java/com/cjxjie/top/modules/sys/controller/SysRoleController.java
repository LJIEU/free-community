package com.cjxjie.top.modules.sys.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.cjxjie.top.common.annotaion.SysLog;
import com.cjxjie.top.common.utils.Constant;
import com.cjxjie.top.common.validator.ValidatorUtils;
import com.cjxjie.top.modules.sys.service.SysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cjxjie.top.modules.sys.entity.SysRoleEntity;
import com.cjxjie.top.modules.sys.service.SysRoleService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;


/**
 * 角色
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Api(tags = "角色")
@Slf4j
@RestController
@RequestMapping("sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 列表
     */
    @ApiOperation("获取所有角色列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }

        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 查询角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R selectRole() {
        HashMap<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己创建的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            map.put("create_user_id", getUserId());
        }

        List<SysRoleEntity> list = sysRoleService.listByMap(map);

        return R.ok().put("list", list);
    }

    /**
     * 根据角色ID获取角色信息及角色菜单ID
     */
    @ApiOperation("根据roleId获取信息")
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity sysRole = sysRoleService.getById(roleId);

        // 查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        // 要将 List<Long> 变成 List<String> 反馈给前端
        List<String> tempMenuIdList = menuIdList.stream().map(Object::toString).collect(Collectors.toList());

        sysRole.setMenuIdList(menuIdList);

        sysRole.setTempMenuIdList(tempMenuIdList);

        return R.ok().put("role", sysRole);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @SysLog("保存角色")
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRoleEntity sysRole) {

        ValidatorUtils.validateEntity(sysRole);

        // 谁创建的当前角色
        sysRole.setCreateUserId(getUserId());
        // 保存
        sysRoleService.saveRole(sysRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @SysLog("修改角色")
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity sysRole) {

        ValidatorUtils.validateEntity(sysRole);

//        log.warn("前端反馈信息:"+sysRole);

        // 谁修改了当前角色
        sysRole.setCreateUserId(getUserId());
//        log.warn("执行修改操作!");
        // 修改
        sysRoleService.update(sysRole);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @SysLog("删除角色")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds) {
//        sysRoleService.removeByIds(Arrays.asList(roleIds));
        sysRoleService.deleteBatch(roleIds);
        return R.ok();
    }

}
