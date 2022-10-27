package com.cjxjie.top.modules.app.utils;

import com.cjxjie.top.common.utils.RedisKeys;
import com.cjxjie.top.common.utils.RedisUtils;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.cjxjie.top.modules.app.vo.UserInvitationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/27 16:37
 */
@Component
public class AppRedis {

    @Lazy
    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(String phone, StringBuilder code, Long expiration) {
        if (code == null) {
            return;
        }
        String key = RedisKeys.getVerifyCode(phone);
        redisUtils.set(key, code, expiration);

    }

    public String getKey(String phone) {
        String key = RedisKeys.getVerifyCode(phone);
        return redisUtils.get(key);
    }

    public void removeKey(String phone) {
        redisUtils.delete(phone);
    }
}
