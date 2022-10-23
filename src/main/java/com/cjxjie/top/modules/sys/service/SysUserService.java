package com.cjxjie.top.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
public interface SysUserService extends IService<SysUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    SysUserEntity queryByUserName(String username);

    boolean saveUser(SysUserEntity user);

    void deleteBatch(Long[] userIds);

    boolean updatePassword(Long userId, String password, String newPassword);

    void update(SysUserEntity user);

    List<Long> queryAllMenuId(Long userId);
}

