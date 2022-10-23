package com.cjxjie.top.modules.app.utils;

import com.cjxjie.top.common.exception.CustomizeException;
import com.cjxjie.top.modules.app.form.JwtForm;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/18 15:02
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "username"; // 声明 钥名
    private static final String CLAIM_KEY_CREATED = "created"; // 声明 创建

    @Autowired
    private JwtForm jwtForm;

    @Value("${jwt.secret}") // 加解密钥
    private String secret/* = jwtForm.getSecret()*/;

    @Value("${jwt.expiration}") // 超时时间
    private Long expiration/* = jwtForm.getExpiration()*/;


    // 密钥
    private Key getSecretKey() {
        byte[] bytes = DatatypeConverter.parseBase64Binary(secret);
        return new SecretKeySpec(bytes, SignatureAlgorithm.HS256.getJcaName());
    }


    // 生成Token
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
//                .setSubject("") // 设置主题
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 从 Token 中获取 JWT 中负载  解析 Token
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            JwtParser build = Jwts.parserBuilder().setSigningKey(getSecretKey()).build(); // 设置解析工具添加密钥
            Jwt parse = build.parse(token); // 解析 Token
            claims = (Claims) parse.getBody();
        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败:{}", token);
            throw new CustomizeException("JWT格式验证失败!");
        }
        return claims;
    }

    // 设置超时时间
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);  // 现在时间 + 86400ms * 1000 ==> 1 天
//        return new Date(System.currentTimeMillis() + 3600 * 1000);
    }


    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
//            System.out.println("claims:" + claims.get(CLAIM_KEY_USERNAME));
            username = (String) claims.get(CLAIM_KEY_USERNAME); // 获取username
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return !expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
}
