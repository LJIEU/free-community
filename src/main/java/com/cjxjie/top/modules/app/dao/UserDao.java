package com.cjxjie.top.modules.app.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@Mapper
@DS(value = "app")
public interface UserDao extends BaseMapper<UserEntity> {
	
}
