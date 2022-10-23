package com.cjxjie.top.modules.app.service;

import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.vo.UserInvitationVo;

import java.util.List;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/06 10:50
 */
public interface PostApiService {
    List<UserInvitationVo> getUserAndPostInfo(List<InvitationEntity> postList);

    UserEntity getPostUser(Long postId);
}
