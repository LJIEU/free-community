package com.cjxjie.top.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.sys.entity.SysRoleMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<Long> queryMenuIdList(Long roleId);

    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    int deleteBatch(Long[] roleIds);
}

