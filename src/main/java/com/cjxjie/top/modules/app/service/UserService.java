package com.cjxjie.top.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.app.entity.CommentEntity;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.vo.CommentAndUserVo;
import com.cjxjie.top.modules.app.vo.RegisterVo;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * 用户信息表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R login(String username, String password);

    UserEntity getByUsername(String username);

    List<CommentAndUserVo> getCommentAndUser(List<CommentEntity> commentList,UserEntity user);

    UserEntity getParent(Long commentId);

    BufferedImage getVerifiCode(String phone);

    void Register(RegisterVo registerVo);
}

