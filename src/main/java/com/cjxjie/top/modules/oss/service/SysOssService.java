package com.cjxjie.top.modules.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.oss.entity.SysOssEntity;

import java.util.Map;

/**
 * 文件上传
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-11 15:10:37
 */
public interface SysOssService extends IService<SysOssEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

