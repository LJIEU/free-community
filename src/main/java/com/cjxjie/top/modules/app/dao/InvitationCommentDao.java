package com.cjxjie.top.modules.app.dao;

import com.cjxjie.top.modules.app.entity.InvitationCommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 帖子与评论关联表
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@Mapper
@CacheNamespace
public interface InvitationCommentDao extends BaseMapper<InvitationCommentEntity> {
	
}
