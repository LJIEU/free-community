package com.cjxjie.top.common.aspect;

import com.cjxjie.top.common.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Redis切面处理类
 */
@Aspect
@Slf4j
@Component
public class RedisAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    //是否开启redis缓存  true开启   false关闭
    @Value("${spring.redis.open}")
    private boolean open;

    // 调用 RedisUtils 下的任意一个方法 都会 执行 此 around 方法【用于查看Redis服务是否开启】
    /* execution 表达式 =》
            第一个 *  ==> 表示返回类型为任意
            com.     ==> 表示 AOP 所切的服务包名
            .*(..)   ==> 表示所有的方法 ()=>表示参数  ..=》表示任何参数
     */
    @Around("execution(* com.cjxjie.top.common.utils.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if(open){
            try{
                result = point.proceed();
            }catch (Exception e){
                logger.error("redis error", e);
                throw new CustomizeException("Redis服务异常");
            }
        }
        return result;
    }
}
