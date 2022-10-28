package com.cjxjie.top.modules.app.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.sf.jsqlparser.parser.feature.Feature;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 帖子信息表
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@DS(value = "app")
@CacheNamespace // 修改 插入 删除 都会重新从 数据库中获取 返回到缓存中
@Mapper
public interface InvitationDao extends BaseMapper<InvitationEntity> {
    List<String> getTopicList();
}
