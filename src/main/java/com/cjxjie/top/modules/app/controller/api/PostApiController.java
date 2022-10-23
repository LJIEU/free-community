package com.cjxjie.top.modules.app.controller.api;

import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.service.InvitationService;
import com.cjxjie.top.modules.app.service.PostApiService;
import com.cjxjie.top.modules.app.vo.UserInvitationVo;
import com.cjxjie.top.modules.sys.form.PostForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/06 10:29
 */
@Api(tags = "帖子信息表")
@RestController
@RequestMapping("app/api")
@Slf4j
public class PostApiController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Lazy
    @Autowired
    private InvitationService invitationService;

    @Autowired
    private PostApiService postApiService;


    @GetMapping("/post/user/{postId}")
    public R getPostUser(@PathVariable(value = "postId") Long postId) {
        UserEntity user = postApiService.getPostUser(postId);
        return R.ok().put("user", user);
    }

    @GetMapping("/post/{id}")
    public R getPostById(@PathVariable(value = "id") Long id) {
        InvitationEntity invitation = invitationService.getPostById(id);
        if (invitation.getState() != 1) {
            invitation = null;
        }
        UserInvitationVo userInvitationVo = new UserInvitationVo();
        assert invitation != null;
        if (invitation.getDocument() != null) {
            Byte[] document = invitation.getDocument();
            byte[] bytes = new byte[document.length];
            int i = 0;
            for (Byte aByte : document) {
                bytes[i++] = aByte;
            }
            String s = new String(bytes);
            log.warn(s);
            userInvitationVo.setDocumentTranslate(s);
        }
        BeanUtils.copyProperties(invitation, userInvitationVo);
        return R.ok().put("post", userInvitationVo);
    }

    /**
     * 获取所有帖子的话题
     */
    @GetMapping("topicList")
    public R getTopicList() {
        List<String> topicList = invitationService.getTopicList();
        return R.ok().put("topicList", topicList);
    }

    /*
        获取已经上架的帖子
     */
    @GetMapping("/postList")
    public R getPostList() {
        // 上架的 帖子
        List<InvitationEntity> postList = invitationService.getPostList();

        // 帖子对应的用户信息
        List<UserInvitationVo> userInvitationVoList = postApiService.getUserAndPostInfo(postList);


        return R.ok().put("forumList", userInvitationVoList);
    }

    @ApiOperation("保存")
    @PostMapping("/savePost")
    public R savePost(@RequestBody PostForm postForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("获取信息:" + authentication.getName());
        String name = authentication.getName(); // 用户名称
        invitationService.savePost(postForm, name);
        return R.ok();
    }
}
