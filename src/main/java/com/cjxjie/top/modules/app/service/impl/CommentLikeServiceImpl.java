package com.cjxjie.top.modules.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.modules.app.dao.CommentLikeDao;
import com.cjxjie.top.modules.app.entity.CommentEntity;
import com.cjxjie.top.modules.app.entity.CommentLikeEntity;
import com.cjxjie.top.modules.app.service.CommentLikeService;
import com.cjxjie.top.modules.app.service.CommentService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/20 17:18
 */
@Log4j
@DS("app")
@Service("commentLikeService")
public class CommentLikeServiceImpl extends ServiceImpl<CommentLikeDao, CommentLikeEntity> implements CommentLikeService {

    @Lazy
    @Autowired
    private CommentService commentService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void isLike(Long userId, Long commentId, Boolean whether) {
        CommentLikeEntity commentLikeEntity = new CommentLikeEntity();
        if (whether) {
            // 添加
            commentLikeEntity.setCommentId(commentId);
            commentLikeEntity.setUserId(userId);
            this.save(commentLikeEntity);
        } else {
            // 删除
            this.remove(new QueryWrapper<CommentLikeEntity>()
                    .eq("comment_id", commentId)
                    .eq("user_id", userId));
        }
        log.warn("保存信息:" + commentLikeEntity.getCommentLikeId());

        // 重新查询列表集合 对评论的点赞数进行更新
        List<CommentLikeEntity> list = baseMapper.selectList(new QueryWrapper<CommentLikeEntity>()
                .eq("comment_id", commentId));
        int size = list.size(); // 这就是点赞数

        CommentEntity comment = commentService.getById(commentId);
        comment.setLikes(size);
        commentService.updateById(comment);
    }

}
