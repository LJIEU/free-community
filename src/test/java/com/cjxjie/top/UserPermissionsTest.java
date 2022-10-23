package com.cjxjie.top;

import com.cjxjie.top.common.utils.Constant;
import com.cjxjie.top.modules.sys.dao.SysMenuDao;
import com.cjxjie.top.modules.sys.dao.SysUserDao;
import com.cjxjie.top.modules.sys.entity.SysMenuEntity;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/07 20:37
 */
@SpringBootTest
public class UserPermissionsTest {
    @Autowired
    SysMenuDao sysMenuDao;

    @Autowired
    SysUserDao sysUserDao;

    @Test
    public void getUserPerms() {
        Long userId = 2L;
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        System.out.println("permsList:" + permsList);
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        System.out.println(permsSet);

    }
}
