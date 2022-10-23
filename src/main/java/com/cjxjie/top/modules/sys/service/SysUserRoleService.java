package com.cjxjie.top.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.sys.entity.SysRoleEntity;
import com.cjxjie.top.modules.sys.entity.SysUserRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户与角色对应关系
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<Long> queryRoleIdList(Long userId);

    void saveOrUpdate(Long userId, List<Long> roleIdList);

    int deleteBatch(Long[] roleIds);

    SysRoleEntity queryRoleIdByUserId(Long userId);
}

