package com.cjxjie.top.modules.app.form;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/18 15:11
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtForm {
    private String secret;
    private Long expiration;
    private String tokenHeader;
    private String tokenHead;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }
}
