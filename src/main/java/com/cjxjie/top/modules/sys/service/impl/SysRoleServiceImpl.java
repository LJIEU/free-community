package com.cjxjie.top.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.common.exception.CustomizeException;
import com.cjxjie.top.common.utils.Constant;
import com.cjxjie.top.modules.sys.dao.SysUserDao;
import com.cjxjie.top.modules.sys.service.SysRoleMenuService;
import com.cjxjie.top.modules.sys.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.sys.dao.SysRoleDao;
import com.cjxjie.top.modules.sys.entity.SysRoleEntity;
import com.cjxjie.top.modules.sys.service.SysRoleService;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service("sysRoleService")
@DS("admin")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        Long createUserId = (Long) params.get("createUserId");

        IPage<SysRoleEntity> page = this.page(
                new Query<SysRoleEntity>().getPage(params),
                new QueryWrapper<SysRoleEntity>()
                        .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
//                        .eq(createUserId != null, "create_user_id", createUserId)
        );

        return new PageUtils(page);
    }


    /**
     * 查询 同创建者ID 的角色
     */
    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }

    /**
     * 保存角色
     */
    // @Transactional(rollbackFor = Exception.class, transactionManager = "adminTransactionManager")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRole(SysRoleEntity sysRole) {
        this.save(sysRole);

        //检查权限是否越权
        check(sysRole);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(), sysRole.getMenuIdList());

    }

    /**
     * 更新角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity sysRole) {
        List<Long> temp = new ArrayList<>(); // 修改角色信息时 sysRole 会将菜单列表置空
        // c.addAll(Arrays.asList(elements)) 效果差不多 但执行速度更快
        Collections.addAll(temp, new Long[sysRole.getMenuIdList().size()]);
        Collections.copy(temp, sysRole.getMenuIdList()); // 完成深拷贝
//        log.warn("修改菜单列表:" + sysRole.getMenuIdList());
        // 修改角色信息
//        log.warn("修改角色信息");
        this.updateById(sysRole);

        // TODO 2022/9/11/9:14 这里会接收 -666666 从前端传递过来的值需改进 临时key, 用于解决tree半选中状态项不能传给后台接口问题
//        log.warn("temp列表:" + temp);

        //检查权限是否越权
//        log.warn("检查是否越权");
        check(sysRole);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(), temp);

    }


    /**
     * 删除角色
     */
/*    @Transactional(rollbackFor = Exception.class, transactionManager = "adminTransactionManager",
            isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)*/
    // @Transactional(rollbackFor = Exception.class, transactionManager = "adminTransactionManager")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        log.warn("需要删除的角色ID:" + Arrays.toString(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);
        log.warn("删除角色与菜单关联");

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
        log.warn("删除角色与用户关联");
    }

    /**
     * 检查是否越权
     */
    private void check(SysRoleEntity role) {
        //如果不是超级管理员，需要判断角色权限是否超过自己的权限
        if (role.getCreateUserId() == Constant.SUPER_ADMIN) {
            return;
        }

        // 查询当前用户的权限menu_id
        List<Long> menuIdList = sysUserDao.queryAllMenuId(role.getCreateUserId());

        role.setMenuIdList(sysRoleMenuService.queryMenuIdList(role.getRoleId()));
//        log.warn("角色菜单ID:" + role.getMenuIdList());

        // 查询是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())) {
            throw new CustomizeException("新增角色的权限,已超出你的权限范围");
        }
    }

}