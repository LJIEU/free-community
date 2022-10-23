package com.cjxjie.top.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.app.entity.UserInvitationEntity;

import java.util.Map;

/**
 * 用户与帖子关联表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
public interface UserInvitationService extends IService<UserInvitationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

