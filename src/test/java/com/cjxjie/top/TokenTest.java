package com.cjxjie.top;

import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.sys.oauth2.TokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/06 16:42
 */
//@SpringBootTest
public class TokenTest {
    @Test
    public void getHex() {
        System.out.println(TokenGenerator.generateValue());
//        System.out.println(new R().put("V","V"));
    }

    @Test
    public void time() {
        // 60*60*12 => 12小时
        long EXPIRE = 3600 * 12;
        // 创建时的时间
        Date now = new Date();

        // 过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        System.out.println(now+"\n"+expireTime);
    }
}
