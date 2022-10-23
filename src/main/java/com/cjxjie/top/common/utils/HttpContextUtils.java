package com.cjxjie.top.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/06 22:32
 */
public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest() {
//        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin() {
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader("Origin");
    }
}
