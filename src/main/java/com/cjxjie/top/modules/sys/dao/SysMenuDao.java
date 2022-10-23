package com.cjxjie.top.modules.sys.dao;

import com.cjxjie.top.modules.sys.entity.SysMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    List<SysMenuEntity> queryNotButtonList();

    List<SysMenuEntity> queryListParentId(@Param("menuId") Long menuId);
}
