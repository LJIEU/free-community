package com.cjxjie.top;

import com.cjxjie.top.common.exception.CustomizeException;
import com.cjxjie.top.common.utils.DateUtils;
import com.cjxjie.top.modules.sys.entity.SysCaptchaEntity;
import com.cjxjie.top.modules.sys.service.SysCaptchaService;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.UUID;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/08 15:51
 */
@SpringBootTest
public class CaptchaTest {

    @Autowired
    private Producer producer;

    @Autowired
    private SysCaptchaService sysCaptchaService;

    /**
     * 获取UUID
     */
    @Test
    public void getCaptcha() {
        String uuid = UUID.randomUUID().toString();
        if (StringUtils.isBlank(uuid)) {
            throw new CustomizeException("UUID不能为空");
        }

        // 生成文字验证码
        String code = producer.createText();
        System.out.println(code);

        SysCaptchaEntity sysCaptchaEntity = new SysCaptchaEntity();
        sysCaptchaEntity.setUuid(uuid);
        sysCaptchaEntity.setCode(code);

        // 5分钟过期
        sysCaptchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        sysCaptchaService.save(sysCaptchaEntity);

        System.out.println(producer.createImage(code));
    }

}
