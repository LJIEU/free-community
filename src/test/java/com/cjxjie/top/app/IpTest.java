package com.cjxjie.top.app;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/16 10:01
 */
public class IpTest {
    public static void main(String[] args) throws IOException {
        // yVyECiHqbGEVlDKMEGwqKfK0eCBqOgTo
        String api = "https://api.map.baidu.com/location/ip?ak=yVyECiHqbGEVlDKMEGwqKfK0eCBqOgTo&ip=39.144.168.10&coor=bd09ll";
//        SendRequest.sendGet(api);
        URL url = null;
        HttpURLConnection httpURLConnection = null;

        BufferedReader input = null;
        StringBuilder buffer = new StringBuilder();

        url = new URL(api);
        input = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String str = null;
        while ((str = input.readLine()) != null) {
            buffer.append(str);
        }
        input.close();

        String result = buffer.toString();
        JSONObject jsonObject = JSON.parseObject(result);
        System.out.println(jsonObject);
        System.out.println("address:" + jsonObject.get("address"));

        // 内容
        JSONObject content = jsonObject.getJSONObject("content");

        // 地址详细
        JSONObject address_detail = content.getJSONObject("address_detail");
        System.out.println("省份:" + address_detail.getString("province"));
        System.out.println("邮件:" + address_detail.getString("adcode"));
        System.out.println("城市:" + address_detail.getString("city"));

        System.out.println("地址:" + content.getString("address"));

        // 坐标
        JSONObject point = content.getJSONObject("point");
        System.out.println("X:"+point.getString("x"));
        System.out.println("Y:"+point.getString("y"));

    }
}
