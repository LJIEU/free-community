package com.cjxjie.top.modules.sys.redis;

import com.cjxjie.top.common.utils.RedisKeys;
import com.cjxjie.top.common.utils.RedisUtils;
import com.cjxjie.top.modules.sys.entity.SysConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/11 14:58
 */
@Component
public class SysRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(SysConfigEntity config) {
        if(config == null){
            return ;
        }
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        redisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    public SysConfigEntity get(String configKey){
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, SysConfigEntity.class);
    }
}
