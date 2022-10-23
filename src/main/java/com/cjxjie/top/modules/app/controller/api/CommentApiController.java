package com.cjxjie.top.modules.app.controller.api;

import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.app.entity.CommentEntity;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.service.CommentService;
import com.cjxjie.top.modules.app.service.UserService;
import com.cjxjie.top.modules.app.vo.CommentAndUserVo;
import com.cjxjie.top.modules.app.vo.CommentVo;
import com.cjxjie.top.modules.app.vo.UserInfoVo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/13 13:31
 */
@Log4j
@RestController
@RequestMapping("app/api/comment")
public class CommentApiController {

    @Lazy
    @Autowired
    private CommentService commentService;

    @Lazy
    @Autowired
    private UserService userService;

    // 查看评论是不需要JWT验证的
    @GetMapping("/list/getParent/{commentId}")
    public R getPrent(@PathVariable Long commentId) {
        UserEntity user = userService.getParent(commentId);

        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setId(user.getUserId());
        userInfoVo.setName(user.getUsername());
        userInfoVo.setAvatarURL(user.getAvatar());
        userInfoVo.setHomeURL("未设置!");
        return R.ok().put("parentUser", userInfoVo);
    }


    @GetMapping("/list/{postId}/{userName}")
    public R commentList(@PathVariable Long postId, @PathVariable String userName) {

        // 判断是否有name参数
        UserEntity user = userService.getByUsername(userName);

        log.warn("传递信息:" + userName);

        // 所有关于当前文章的评论
        List<CommentEntity> commentList = commentService.getCommentList(postId);


        // 重新组装信息 需要用户基本信息内容
        List<CommentAndUserVo> list = userService.getCommentAndUser(commentList, user);
        return R.ok().put("commentList", list);
    }


    // 其他操作需要
    @PostMapping("/saveComment")
    public R saveComment(@RequestBody CommentVo commentVo) {
        System.out.println(commentVo);
        commentService.save(commentVo);
        return R.ok();
    }
}
