package com.cjxjie.top.modules.sys.oauth2;

import com.cjxjie.top.modules.sys.entity.SysUserEntity;
import com.cjxjie.top.modules.sys.entity.SysUserTokenEntity;
import com.cjxjie.top.modules.sys.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/06 22:08
 * <p>
 * 认证
 */
@Slf4j
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    /**
     * 授权 【验证权限时调用】
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.warn("OAuth2Realm中的授权方法doGetAuthorizationInfo被调用~~");
        SysUserEntity user = (SysUserEntity) principalCollection.getPrimaryPrincipal();
        Long userId = user.getUserId();

        // 用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 身份验证 【登录时调用】
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);

        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new IncorrectCredentialsException("Token失效,请重新登录");
        }

        SysUserEntity user = shiroService.queryUser(tokenEntity.getUserId());
        if (user.getStatus() == 0) {
            throw new LockedAccountException("状态被锁定,请联系管理员");
        }

//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return new SimpleAuthenticationInfo(user, accessToken, getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }
}
