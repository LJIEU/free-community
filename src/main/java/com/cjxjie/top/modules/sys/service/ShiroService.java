package com.cjxjie.top.modules.sys.service;

/*
  @author 刘杰
 * @version 1.0
 * @since 2022/09/07 20:11
 */

import com.cjxjie.top.modules.sys.entity.SysUserEntity;
import com.cjxjie.top.modules.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * 认证业务
 */
public interface ShiroService {

    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     */
    SysUserEntity queryUser(Long userId);
}
