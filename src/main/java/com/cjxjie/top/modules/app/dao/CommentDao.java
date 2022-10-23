package com.cjxjie.top.modules.app.dao;

import com.cjxjie.top.modules.app.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论信息表

 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
	
}
