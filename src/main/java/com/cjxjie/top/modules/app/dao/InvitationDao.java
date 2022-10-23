package com.cjxjie.top.modules.app.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 帖子信息表
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@DS(value = "app")
@Mapper
public interface InvitationDao extends BaseMapper<InvitationEntity> {

    List<String> getTopicList();

}
