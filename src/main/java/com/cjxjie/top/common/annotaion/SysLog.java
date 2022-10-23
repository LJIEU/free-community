package com.cjxjie.top.common.annotaion;

import java.lang.annotation.*;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/08 16:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
