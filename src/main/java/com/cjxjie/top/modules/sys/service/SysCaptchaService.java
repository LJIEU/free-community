package com.cjxjie.top.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.modules.sys.entity.SysCaptchaEntity;

import java.awt.image.BufferedImage;

/**
 * 系统验证码
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
public interface SysCaptchaService extends IService<SysCaptchaEntity> {

//    PageUtils queryPage(Map<String, Object> params);

    BufferedImage getCaptcha(String uuid);

    boolean validate(String uuid, String captcha);

}

