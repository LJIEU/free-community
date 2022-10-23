package com.cjxjie.top.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.common.utils.Constant;
import com.cjxjie.top.common.utils.MapUtils;
import com.cjxjie.top.modules.sys.service.SysRoleMenuService;
import com.cjxjie.top.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.sys.dao.SysMenuDao;
import com.cjxjie.top.modules.sys.entity.SysMenuEntity;
import com.cjxjie.top.modules.sys.service.SysMenuService;


@Service("sysMenuService")
@DS("admin")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysMenuEntity> page = this.page(
                new Query<SysMenuEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<SysMenuEntity> getUserMenuId(Long userId) {
        // 如果是超级管理员 直接返回
        if (userId == Constant.SUPER_ADMIN) {
            return getMenuList(null);
        }
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getMenuList(menuIdList);
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> queryListParentId(Long menuId) {
        return baseMapper.queryListParentId(menuId);
    }

    @Override
    public void delete(Long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        sysRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
    }

    /*
        获取拥有的菜单列表
     */
    private List<SysMenuEntity> getMenuList(List<Long> menuIdList) {
        // 查询拥有的所有菜单
        List<SysMenuEntity> menus = this.baseMapper.selectList(new QueryWrapper<SysMenuEntity>()
                .in(Objects.nonNull(menuIdList), "menu_id", menuIdList)
                .in("type", 0, 1)
        );

        // 将ID和菜单绑定
        HashMap<Long, SysMenuEntity> menuMap = new HashMap<>(12);
        for (SysMenuEntity menu : menus) {
            menuMap.put(menu.getMenuId(), menu);
        }

        // 组装菜单的层级关系
        Iterator<SysMenuEntity> iterator = menus.iterator();
        while (iterator.hasNext()) {
            SysMenuEntity menu = iterator.next();
            // 获取菜单父节点
            SysMenuEntity parent = menuMap.get(menu.getParentId());

            // 如果有父节点
            if (Objects.nonNull(parent)) {
                parent.getList().add(menu);

                // 将当前遍历的菜单从当前节点移除
                iterator.remove();
            }
        }
        return menus;
    }


}