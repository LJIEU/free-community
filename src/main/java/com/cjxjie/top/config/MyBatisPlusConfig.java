package com.cjxjie.top.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/04 17:25
 */
@Configuration
@MapperScan("com.cjxjie.top.modules.*.dao")
public class MyBatisPlusConfig {

    /**
     * 分页插件
     */
// 分页拦截器
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 乐观锁
//        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        // 分页锁
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;

    }

    @Bean
    public IKeyGenerator keyGenerator() {
        return new H2KeyGenerator();
    }

}
