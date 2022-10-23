package com.cjxjie.top.modules.app.controller;

import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.service.CommentLikeService;
import com.cjxjie.top.modules.app.service.UserService;
import com.cjxjie.top.modules.app.vo.UserCommentVo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/20 17:13
 */
@Log4j
@RestController
@RequestMapping("app/api/commentLike")
public class CommentLikeController {

    @Autowired
    private UserService userService;

    private final CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("/isLike")
    public R isLike(@RequestBody UserCommentVo userCommentVo) {
        log.warn("当前用户:" + "\t传递信息:" + userCommentVo);
        UserEntity user = userService.getByUsername(userCommentVo.getUserName());


        // 需要登录用户ID 和 评论ID
        commentLikeService.isLike(user.getUserId(), userCommentVo.getCommentId(), userCommentVo.getWhether());

        return R.ok().put("isLike", userCommentVo.getWhether());
    }

}
