package com.cjxjie.top.common.utils;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/11 14:32
 */
public class RedisKeys {
    public static String getSysConfigKey(String key) {
        return "sys:config:" + key;
    }

    public static String getAppPostListKey(String key) {
        return "app:post:" + key;
    }

    public static String getVerifyCode(String phone) {
        return "app:verify_code:" + phone;
    }
}
