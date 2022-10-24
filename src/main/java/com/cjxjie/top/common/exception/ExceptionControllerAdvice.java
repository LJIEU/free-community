package com.cjxjie.top.common.exception;

import com.cjxjie.top.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author 刘杰
 * @version 1.0
 * @Date: 2022/7/10 20:40
 */
@Slf4j
@ControllerAdvice(basePackages = "com.cjxjie.top")
@RestControllerAdvice // 统一异常处理
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    public R handleValidException(Exception e) {
        log.error("捕获异常信息:");
        System.out.println(e.getMessage());
        return R.error().put("error", e.getMessage());
    }
    @ExceptionHandler(value = CustomizeException.class)
    public R customizeException(Exception e) {
        log.error("捕获异常信息:");
        System.out.println(e.getMessage());
        return R.error().put("402", e.getMessage());
    }
}
