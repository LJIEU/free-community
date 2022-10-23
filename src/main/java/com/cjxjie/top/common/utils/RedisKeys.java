package com.cjxjie.top.common.utils;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/11 14:32
 */
public class RedisKeys {
    public static String getSysConfigKey(String key){
        return "sys:config:"+key;
    }
}
