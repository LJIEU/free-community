package com.cjxjie.top.modules.app.dao;

import com.cjxjie.top.modules.app.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 帖子分类
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    List<String> nameList();
}
