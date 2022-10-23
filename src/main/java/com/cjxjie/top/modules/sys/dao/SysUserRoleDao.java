package com.cjxjie.top.modules.sys.dao;

import com.cjxjie.top.modules.sys.entity.SysUserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户与角色对应关系
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

    List<Long> queryRoleIdList(@Param(value = "userId") Long userId);

    int deleteBatch(Long[] roleIds);
}
