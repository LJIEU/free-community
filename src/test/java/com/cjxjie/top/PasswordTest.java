package com.cjxjie.top;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.jupiter.api.Test;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/08 14:20
 */

public class PasswordTest {
    @Test
    public void getPassword() {
        String password = "123456";
        String salt = "YzcmCZNvbXocrsz9dm8e";

        String s = new Sha256Hash(password, salt).toHex();
        System.out.println(s);
    }
}
