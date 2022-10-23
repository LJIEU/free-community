package com.cjxjie.top.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<Long> queryRoleIdList(Long createUserId);

    void deleteBatch(Long[] roleIds);

    void saveRole(SysRoleEntity sysRole);

    void update(SysRoleEntity sysRole);

}

