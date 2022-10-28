package com.cjxjie.top.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjxjie.top.modules.app.entity.CommentGroupEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/12 21:16
 */
@Mapper
public interface CommentGroupDao extends BaseMapper<CommentGroupEntity> {
}
