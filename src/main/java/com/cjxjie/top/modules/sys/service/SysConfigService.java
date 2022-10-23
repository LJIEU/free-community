package com.cjxjie.top.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.sys.entity.SysConfigEntity;

import java.util.Map;

/**
 * 系统配置信息表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveConfig(SysConfigEntity config);

    void update(SysConfigEntity config);

    void deleteBatch(Long[] ids);

    <T> T getConfigObject(String key, Class<T> tClass);

    void updateValueByKey(String key, String toJson);
}

