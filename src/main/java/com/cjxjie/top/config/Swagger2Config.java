package com.cjxjie.top.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/06 15:13
 */
@Configuration
@EnableSwagger2 //  开启 Swagger2
public class Swagger2Config {

    @Bean
    public Docket UserApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统用户信息")
                .apiInfo(adminApiInfo())
                .select()
//                .paths(PathSelectors.regex("/sys/user/*/.*"))
                .paths(PathSelectors.regex("/es/*/.*"))
                .build();
    }


    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder().title("网站的API文档")
                .description("文本描述了网站的api接口定义")
                .version("1.0")
                .contact(new Contact("JIE", "联系人电话或网页地址", "联系人邮箱"))
                .build();
    }
}
