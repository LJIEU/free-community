package com.cjxjie.top.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/12 13:43
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {

    // 获取配置文件中的信息
    @Value("${picture.show}")
    private String showPath;

    @Value("${picture.up-path}")
    private String upPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(showPath).addResourceLocations("file:"+upPath);
    }

}