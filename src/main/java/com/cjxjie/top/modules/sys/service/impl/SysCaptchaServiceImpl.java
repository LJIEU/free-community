package com.cjxjie.top.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjxjie.top.common.exception.CustomizeException;
import com.cjxjie.top.common.utils.DateUtils;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.modules.sys.dao.SysCaptchaDao;
import com.cjxjie.top.modules.sys.entity.SysCaptchaEntity;
import com.cjxjie.top.modules.sys.service.SysCaptchaService;
import com.google.code.kaptcha.Producer;


@Service("sysCaptchaService")
@DS("admin")
public class SysCaptchaServiceImpl extends ServiceImpl<SysCaptchaDao, SysCaptchaEntity> implements SysCaptchaService {

    @Autowired
    private Producer producer;

    /**
     * 获取UUID
     */
    @Override
    public BufferedImage getCaptcha(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            throw new CustomizeException("UUID不能为空");
        }

        // 生成文字验证码
        String code = producer.createText();

        SysCaptchaEntity sysCaptchaEntity = new SysCaptchaEntity();
        sysCaptchaEntity.setUuid(uuid);
        sysCaptchaEntity.setCode(code);

        // 5分钟过期
        sysCaptchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        this.save(sysCaptchaEntity);

        return producer.createImage(code);
    }

    /**
     * 验证验证码
     */
    @Override
    public boolean validate(String uuid, String captcha) {
        SysCaptchaEntity captchaEntity = this.getOne(new QueryWrapper<SysCaptchaEntity>().eq("uuid", uuid));
        if (captchaEntity == null) {
            return false;
        }
        // 删除验证码【验证码只能使用一次】
        this.removeById(uuid);

        return captchaEntity.getCode().equalsIgnoreCase(captcha) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis();
    }

}