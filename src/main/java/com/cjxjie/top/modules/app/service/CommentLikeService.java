package com.cjxjie.top.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.modules.app.entity.CommentLikeEntity;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/20 17:14
 */
public interface CommentLikeService extends IService<CommentLikeEntity> {

    void isLike(Long userId, Long commentId, Boolean whether);
}
