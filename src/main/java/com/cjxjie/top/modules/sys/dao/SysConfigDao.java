package com.cjxjie.top.modules.sys.dao;

import com.cjxjie.top.modules.sys.entity.SysConfigEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息表
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
@Mapper
public interface SysConfigDao extends BaseMapper<SysConfigEntity> {

    void updateValueByKey(@Param("paramKey") String key, @Param("paramValue") String value);

    SysConfigEntity queryByKey(@Param("paramKey") String key);
}
