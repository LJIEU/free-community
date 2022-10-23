package com.cjxjie.top.modules.sys.dao;

import com.cjxjie.top.modules.sys.entity.SysRoleMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

    List<Long> queryMenuIdList(@Param("roleId") Long roleId);

    int deleteBatch(Long[] roleIds);
}
