package com.cjxjie.top.common.utils;

import java.util.HashMap;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/09 9:13
 */
public class MapUtils extends HashMap<String, Object> {
    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
