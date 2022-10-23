package com.cjxjie.top.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjxjie.top.modules.app.dao.InvitationDao;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/06 13:51
 */
@SpringBootTest
public class BlobTest {

    @Autowired
    private InvitationDao invitationDao;

    @Test
    public void setBlob() throws IOException {
        // 写入
        String text = "https://pic2.zhimg.com/50/v2-906b44107bdb20ca768a79853c66725b_400x224.jpg";
        byte[] bytes1 = text.getBytes(StandardCharsets.UTF_8);
        Byte[] bytes = new Byte[bytes1.length];
        int i = 0;
        for (byte b : bytes1) {
            bytes[i++] = b;
        }
        InvitationEntity invitationEntity = new InvitationEntity();
//        invitationEntity.setInvitationId(3L);
        invitationEntity.setDocument(bytes);
        invitationDao.update(invitationEntity, new QueryWrapper<InvitationEntity>().eq("invitation_id", 3L));

        // 读取
        i = 0;
        byte[] bytes2 = new byte[bytes.length];
        for (Byte aByte : bytes) {
            bytes2[i++] = aByte;
        }
        String s1 = new String(bytes2);
        System.out.println(s1);
/*
        URL url = new URL(text);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5 * 1000);
        InputStream inputStream = connection.getInputStream(); // 输入

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // 输出
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        byte[] bytes = outputStream.toByteArray();

        byte[] decode = Base64.getDecoder().decode(text);

        String s = Base64.getEncoder().encodeToString(decode);
        System.out.println(s);
        inputStream.close();
        outputStream.close();
*/
    }
}
