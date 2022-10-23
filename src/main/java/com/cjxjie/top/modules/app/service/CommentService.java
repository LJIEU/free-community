package com.cjxjie.top.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.app.entity.CommentEntity;
import com.cjxjie.top.modules.app.vo.CommentVo;

import java.util.List;
import java.util.Map;

/**
 * 评论信息表

 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(CommentVo commentVo);

    List<CommentEntity> getCommentList(Long postId);
}

