package com.cjxjie.top.modules.sys.controller;

import java.util.*;

import cn.hutool.http.HttpStatus;
import com.cjxjie.top.common.annotaion.SysLog;
import com.cjxjie.top.common.exception.CustomizeException;
import com.cjxjie.top.common.utils.Constant;
import com.cjxjie.top.modules.sys.entity.SysRoleEntity;
import com.cjxjie.top.modules.sys.entity.SysRoleMenuEntity;
import com.cjxjie.top.modules.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cjxjie.top.modules.sys.entity.SysMenuEntity;
import com.cjxjie.top.common.utils.R;


/**
 * 菜单管理
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Api(tags = "菜单管理")
@Slf4j
@RestController
@RequestMapping("sys/menu")
public class SysMenuController extends AbstractController {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public R navMenu() {
//        log.warn("导航菜单被调用~~");

        // 获取当前用户的 菜单列表
        List<SysMenuEntity> menus = sysMenuService.getUserMenuId(getUserId());

        // 查看当前用户的 权限
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return R.ok().put("permissions", permissions).put("menuList", menus);
    }

    /**
     * 列表
     */
    @ApiOperation("获取所有菜单管理列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuEntity> list(@RequestParam Map<String, Object> params) {
        List<SysMenuEntity> menuList = sysMenuService.list();
        HashMap<Long, SysMenuEntity> menuMap = new HashMap<>(12);
        for (SysMenuEntity s : menuList) {
            menuMap.put(s.getMenuId(), s);
        }
        for (SysMenuEntity s : menuList) {
            SysMenuEntity parent = menuMap.get(s.getParentId());
            if (Objects.nonNull(parent)) {
                s.setParentName(parent.getName());
            }
        }
//        log.warn("获取所有菜单管理列表~");
        System.out.println(menuList);
        return menuList;
    }

    /**
     * 选择菜单
     */
    @GetMapping("select")
    @RequiresPermissions("sys:menu:select")
    public R selectMenu() {
        // 获取没有按钮的菜单列表 typ != 2 都是菜单列表
        List<SysMenuEntity> menus = sysMenuService.queryNotButtonList();

        // 设置顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);

        menus.add(root);
        return R.ok().put("menuList", menus);
    }


    /**
     * 信息
     */
    @ApiOperation("根据menuId获取信息")
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity sysMenu = sysMenuService.getById(menuId);
        return R.ok().put("menu", sysMenu);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody SysMenuEntity sysMenu) {
        // 数据效验
        verifyForm(sysMenu);

        log.warn("添加菜单信息:" + sysMenu);
        // 保存菜单
        sysMenuService.save(sysMenu);

        // 查询角色
        SysRoleEntity role = sysUserRoleService.queryRoleIdByUserId(getUserId());

        // 给当前角色添加 当前创建的菜单
        SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
        sysRoleMenuEntity.setMenuId(sysMenu.getMenuId());
        sysRoleMenuEntity.setRoleId(role.getRoleId());
        sysRoleMenuService.save(sysRoleMenuEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody SysMenuEntity sysMenu) {
        // 数据效验
        verifyForm(sysMenu);

        sysMenuService.updateById(sysMenu);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @SysLog("删除菜单")
    @DeleteMapping("/delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    public R delete(@PathVariable Long menuId) {
        if (menuId <= 31) {
            return R.error(HttpStatus.HTTP_INTERNAL_ERROR, "系统菜单,不能删除");
        }

        // 判断是否有子菜单或按钮
        List<SysMenuEntity> menus = sysMenuService.queryListParentId(menuId);
        if (menus.size() > 0) {
            return R.error(HttpStatus.HTTP_INTERNAL_ERROR, "请先删除子菜单!");
        }
//        sysMenuService.removeByIds(Arrays.asList(menuId));

        sysMenuService.delete(menuId);
        return R.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuEntity menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new CustomizeException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new CustomizeException("上级菜单不能为空");
        }

        //菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new CustomizeException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new CustomizeException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new CustomizeException("上级菜单只能为菜单类型");
            }
            return;
        }
    }

}
