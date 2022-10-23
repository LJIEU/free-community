package com.cjxjie.top.common.validator;

import com.cjxjie.top.common.exception.CustomizeException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new CustomizeException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new CustomizeException(message);
        }
    }
}
