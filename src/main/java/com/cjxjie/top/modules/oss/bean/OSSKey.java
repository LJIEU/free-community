package com.cjxjie.top.modules.oss.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/11 16:07
 */
@Data
@Component
@ConfigurationProperties(prefix = "alibaba.oss")
public class OSSKey {
    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String bucketName;

}
