package com.cjxjie.top.modules.sys.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/06 18:27
 *
 * Token
 */
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
