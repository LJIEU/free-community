package com.cjxjie.top;

import com.cjxjie.top.modules.app.form.JwtForm;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/18 8:37
 * JWT 测试
 */
@SpringBootTest
public class JwtTest {


    @Value("${jwt.secret}")
    private  String secret;

    @Value("${jwt.expiration}")
    private  String expiration;

    @Test
    public void generatorJWT() {
        Date nowTime = new Date();

        System.out.println(secret + "\t" + expiration);
//        String s = "ukc8BDbRigUDaY6pZFfWus2jZWLPHOJIELIUdjfdsaljflsdfjlsdjfsldfjsldfjlsdfsdf";
        String s = "JIE12345678901234567890123456";
        String str = Sha256Hash.fromBase64String(s).toHex(); // s 长度 > 28
        // 1个字节 占 8位
//        String s = "ukc8BDbRigUDaY6pZFfWus2jZWLPHOJIELI";
        byte[] bytes = DatatypeConverter.parseBase64Binary(str);
        System.out.println("解密长度:" + bytes.length);
        Key key = new SecretKeySpec(bytes, SignatureAlgorithm.HS256.getJcaName());
        System.out.println("字符长度:" + s.length() + "\t字符:" + s);
        System.out.println("加密后的字符长度:" + str.length() + "\t字符:" + str);

        HashMap<String, Object> map = new HashMap<>();
        map.put("数据1", "Tom");
        map.put("数据2", "创建时间" + new Date());

        int expired = 60 * 60 * 3; // 3个小时后
        String jwtToken = Jwts.builder()
                .setClaims(map) // 添加需要荷载的数据信息
                .setHeaderParam("typ", "JWT")
//                .setSubject("测试用例") // 设置主题
                .setIssuedAt(nowTime) // 创建时间
                .setExpiration(new Date(nowTime.getTime() + expired * 1000)) // 过期时间
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println(jwtToken);

        System.out.println("================解析===============");
        /*解析JWT*/
        byte[] base64Binary = DatatypeConverter.parseBase64Binary(str);
        SecretKeySpec secretKeySpec = new SecretKeySpec(base64Binary, SignatureAlgorithm.HS256.getJcaName());
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKeySpec).build();
        Jwt parse = jwtParser.parse(jwtToken);
        Claims claims = (Claims) parse.getBody();
        System.out.println(claims.toString());
        /*
        System.out.println(new Sha256Hash("free-community").toHex());
        HMACSigner signer = HMACSigner.newSHA512Signer(new Sha256Hash("free-community").toHex()); // 加密
        JWT jwt = new JWT()
                .setIssuer("JIE") // 作者
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC)) // 创建时间
                .setSubject("APP") // 主题
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(60)); // 过期时间

        String encode = JWT.getEncoder().encode(jwt, signer); // 生成加密后的字符串
        System.out.println(encode);

        System.out.println("========验证=========");
        HMACVerifier verifier = HMACVerifier.newVerifier(new Sha256Hash("free-community").toHex());
        JWT jwt2 = JWT.getDecoder().decode(encode, verifier);
        System.out.println(jwt2.subject + "\t" + new Sha256Hash("free-community").toHex());

        System.out.println("=================");
        String json = "{" +
                "\"user\":\"JIE\""+
                "}";
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        JSONWebKey jsonWebKey = Mapper.deserialize(bytes, JSONWebKey.class);
        PublicKey publicKey = JSONWebKey.parse(jsonWebKey);
        System.out.println(publicKey);

*/
    }
}
