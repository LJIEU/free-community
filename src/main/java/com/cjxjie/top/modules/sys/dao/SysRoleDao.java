package com.cjxjie.top.modules.sys.dao;

import com.cjxjie.top.modules.sys.entity.SysRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    List<Long> queryRoleIdList(Long createUserId);
}
