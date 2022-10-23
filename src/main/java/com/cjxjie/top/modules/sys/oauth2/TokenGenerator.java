package com.cjxjie.top.modules.sys.oauth2;

import com.cjxjie.top.common.exception.CustomizeException;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/06 18:31
 * <p>
 * Token 生成
 */
public class TokenGenerator {
    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    // 一定要 16个字符长度
    private static final char[] hexCode = "jie0123456789liu".toCharArray();

    public static String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new CustomizeException("生成Token失败", e);
        }
    }

}
