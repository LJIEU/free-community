package com.cjxjie.top.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.common.utils.Constant;
import com.cjxjie.top.modules.sys.dao.SysMenuDao;
import com.cjxjie.top.modules.sys.dao.SysUserDao;
import com.cjxjie.top.modules.sys.dao.SysUserTokenDao;
import com.cjxjie.top.modules.sys.entity.SysMenuEntity;
import com.cjxjie.top.modules.sys.entity.SysUserEntity;
import com.cjxjie.top.modules.sys.entity.SysUserTokenEntity;
import com.cjxjie.top.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/07 20:12
 */
@Service
@DS("admin")
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            // 查询所有 菜单
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            // 获取 菜单中的 授权值 【perms】
            // 比如查看操作:  sys:schedule:list,sys:schedule:info
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            // 查询 用户权限 通过 sys_user_role 和 sys_role_menu 和 sys_menu 三表联合查询==》 授权值:perms
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            // 以 , 为间隔 添加到 Set 集合中
            /*
                如果 userID = 2 ==》 sys:menu:update,sys:menu:select, sys:log:list
             */
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet; // ==> sys:menu:update, sys:menu:select, sys:log:list
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}
