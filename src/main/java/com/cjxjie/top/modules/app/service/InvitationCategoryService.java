package com.cjxjie.top.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.app.entity.InvitationCategoryEntity;

import java.util.Map;

/**
 * 帖子和分类关联表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
public interface InvitationCategoryService extends IService<InvitationCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

