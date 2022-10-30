package com.cjxjie.top.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.cjxjie.top.modules.app.vo.UserInvitationVo;
import com.cjxjie.top.modules.es.docment.ESUserAndPost;
import com.cjxjie.top.modules.sys.form.PostForm;

import java.util.List;
import java.util.Map;

/**
 * 帖子信息表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
public interface InvitationService extends IService<InvitationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<UserInvitationVo> getUserAndPost(List<?> list);

    List<InvitationEntity> getPostList();

    void savePost(PostForm postForm,String name);

    List<String> getTopicList();

    InvitationEntity getPostById(Long id);

}

