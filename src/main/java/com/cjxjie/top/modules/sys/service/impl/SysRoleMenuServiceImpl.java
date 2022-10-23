package com.cjxjie.top.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.sys.dao.SysRoleMenuDao;
import com.cjxjie.top.modules.sys.entity.SysRoleMenuEntity;
import com.cjxjie.top.modules.sys.service.SysRoleMenuService;


@Slf4j
@Service("sysRoleMenuService")
@DS("admin")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleMenuEntity> page = this.page(
                new Query<SysRoleMenuEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }

    @Override
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(new Long[]{roleId});

        if (menuIdList.size() == 0) {
            return;
        }

        log.warn("执行保存或更新操作:");
        log.warn("修改角色ID:" + roleId);
        log.warn("需要修改的菜单列表:" + menuIdList);

        //保存角色与菜单关系
        for (Long menuId : menuIdList) {
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);

            this.save(sysRoleMenuEntity);
        }

    }

    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}