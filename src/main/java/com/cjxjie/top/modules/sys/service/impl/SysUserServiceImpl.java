package com.cjxjie.top.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.common.exception.CustomizeException;
import com.cjxjie.top.common.utils.Constant;
import com.cjxjie.top.modules.sys.service.SysRoleService;
import com.cjxjie.top.modules.sys.service.SysUserRoleService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.sys.dao.SysUserDao;
import com.cjxjie.top.modules.sys.entity.SysUserEntity;
import com.cjxjie.top.modules.sys.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;


@Service("sysUserService")
@DS("admin")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        Long createUserId = (Long) params.get("createUserId");

//        log.warn(createUserId == null ? "创建者为NULL!" : "不为null"+createUserId);

        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .eq(createUserId != null, "create_user_id", createUserId)
        );

        return new PageUtils(page);
    }


    /**
     * 获取用户信息
     */
    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    @Transactional
    public boolean saveUser(SysUserEntity user) {
//        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        if (this.save(user)) {
            //检查角色是否越权
            checkRole(user);

            //保存用户与角色关系
            sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
            return true;
        }
        return false;
    }

    @Override
    public void deleteBatch(Long[] userIds) {
        this.removeByIds(Arrays.asList(userIds));
    }

    /**
     * 修改密码
     */
    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>().eq("user_id", userId)
                .eq("password", password);
        return baseMapper.update(userEntity, queryWrapper) == 1;
    }

    @Override
    @Transactional
    public void update(SysUserEntity user) {
        // 如果密码不修改就设置为 null
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            // 加密后修改
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        this.updateById(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserEntity user) {
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            log.warn("用户的角色列表为NUll");
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (user.getCreateUserId() == Constant.SUPER_ADMIN) {
            log.warn("用户的角色列表为NUll");
            return;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new CustomizeException("新增用户所选角色，不是本人创建");
        }
    }

}