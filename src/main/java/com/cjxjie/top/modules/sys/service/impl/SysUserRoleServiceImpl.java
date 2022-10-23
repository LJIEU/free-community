package com.cjxjie.top.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.common.utils.MapUtils;
import com.cjxjie.top.modules.sys.dao.SysRoleDao;
import com.cjxjie.top.modules.sys.entity.SysRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.sys.dao.SysUserRoleDao;
import com.cjxjie.top.modules.sys.entity.SysUserRoleEntity;
import com.cjxjie.top.modules.sys.service.SysUserRoleService;
import org.springframework.transaction.annotation.Transactional;


@Service("sysUserRoleService")
@DS("admin")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserRoleEntity> page = this.page(
                new Query<SysUserRoleEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return baseMapper.queryRoleIdList(userId);
    }

    @Override
   // @Transactional(rollbackFor = Exception.class, transactionManager = "adminTransactionManager")
    @Transactional(rollbackFor = Exception.class)

    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        this.removeByMap(new MapUtils().put("user_id", userId));

        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }

        //保存用户与角色关系
        for(Long roleId : roleIdList){
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);

            this.save(sysUserRoleEntity);
        }
    }

    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public SysRoleEntity queryRoleIdByUserId(Long userId) {
        SysUserRoleEntity userRoleEntity = baseMapper.selectOne(new QueryWrapper<SysUserRoleEntity>()
                .eq("user_id", userId));
        return sysRoleDao.selectOne(new QueryWrapper<SysRoleEntity>().eq("role_id", userRoleEntity.getRoleId()));
    }

}