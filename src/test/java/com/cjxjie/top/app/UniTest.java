package com.cjxjie.top.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniMessage;
import com.apistd.uni.sms.UniSMS;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/22 15:45
 * 短信服务
 */
public class UniTest {
    public static String ACCESS_KEY_ID = "jp3WEzyYZz4RNvWiZ1XpPvwKh7u9z77cj1e624DFWkKtzoQs1";
    private static String ACCESS_KEY_SECRET = "your access key secret";

    public static void main(String[] args) {
        // 初始化
        Uni.init(ACCESS_KEY_ID, ACCESS_KEY_SECRET); // 若使用简易验签模式仅传入第一个参数即可

        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<String, String>();
        templateData.put("code", "6666");

        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo("14717477909")
                .setSignature("论坛")
                .setTemplateId("pub_verif_basic")
                .setTemplateData(templateData);

        // 发送短信
        try {
            UniResponse res = message.send();
            System.out.println(res);

        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }
    }
}
