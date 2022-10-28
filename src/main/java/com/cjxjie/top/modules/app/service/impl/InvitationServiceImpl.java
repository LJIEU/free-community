package com.cjxjie.top.modules.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.modules.app.dao.UserDao;
import com.cjxjie.top.modules.app.dao.UserInvitationDao;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.entity.UserInvitationEntity;
import com.cjxjie.top.modules.app.service.UserInvitationService;
import com.cjxjie.top.modules.app.vo.UserInvitationVo;
import com.cjxjie.top.modules.sys.form.PostForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.app.dao.InvitationDao;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.cjxjie.top.modules.app.service.InvitationService;


@Service("invitationService")
@DS(value = "app")
public class InvitationServiceImpl extends ServiceImpl<InvitationDao, InvitationEntity> implements InvitationService {
    @Autowired
    private UserInvitationDao userInvitationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserInvitationService userInvitationService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String onList = (String) params.get("onList");
        String offList = (String) params.get("offList");
        String notReviewed = (String) params.get("notReviewed");
        log.warn("上下架信息:\t" + onList + "\t" + offList + "\t" + notReviewed);
        Object key = params.get("key");
        IPage<InvitationEntity> page = this.page(
                new Query<InvitationEntity>().getPage(params),
                new QueryWrapper<InvitationEntity>().like(StringUtils.isNotBlank((String) key), "title", key)
                        .eq(offList.equals("true"), "state", 0) // 下架
                        .eq(onList.equals("true"), "state", 1) // 上架
                        .eq(notReviewed.equals("true"), "state", 2) // 审核
        );

        return new PageUtils(page);
    }

    @Override
    public List<UserInvitationVo> getUserAndPost(List<?> list) {
        return list.stream().map(v -> {
            if (v instanceof InvitationEntity) {
                UserInvitationVo userInvitationVo = getUser(((InvitationEntity) v).getInvitationId());
//                System.out.println("帖子信息:" + v);
                BeanUtils.copyProperties(v, userInvitationVo);
                return userInvitationVo;
            }
            return null;
        }).collect(Collectors.toList());
    }

    @Override
    public List<InvitationEntity> getPostList() {
        return  baseMapper.selectList(new QueryWrapper<InvitationEntity>().eq("state", 1));
    }

    @Override
    public void savePost(PostForm postForm, String name) {
        log.warn(postForm.toString());
        String content = postForm.getContent();
        String title = postForm.getTitle();
        String topic = postForm.getTopic();
        String description = postForm.getDescription();

        // 转字节
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        Byte[] newByte = new Byte[bytes.length];
        int i = 0;
        for (byte aByte : bytes) {
            newByte[i++] = aByte;
        }

        InvitationEntity invitationEntity = new InvitationEntity();
        invitationEntity.setDocument(newByte)
                .setTitle(title)
                .setTopic(topic)
                .setContent(description);
        // 保存帖子
        this.save(invitationEntity);

        // 帖子 和 用户 绑定
        Long invitationId = invitationEntity.getInvitationId();
        log.warn("生成ID:" + invitationId);
        UserInvitationEntity userInvitationEntity = new UserInvitationEntity();
        UserEntity user = userDao.selectOne(new QueryWrapper<UserEntity>().eq("username", name));
        Long userId = user.getUserId();
        userInvitationEntity.setUserId(userId);
        userInvitationEntity.setInvitationId(invitationId);
        userInvitationService.save(userInvitationEntity);
    }

    @Override
    public List<String> getTopicList() {
        return baseMapper.getTopicList();
    }

    @Override
    public InvitationEntity getPostById(Long id) {
        InvitationEntity invitation = baseMapper.selectOne(new QueryWrapper<InvitationEntity>()
                .eq("invitation_id", id));
        return invitation;
    }

    public UserInvitationVo getUser(Long invitationId) {
        // 根据 帖子ID 搜索 用户
        UserInvitationEntity userInvitationEntity = userInvitationDao.selectOne(new QueryWrapper<UserInvitationEntity>()
                .eq("invitation_id", invitationId));

        // 查询用户信息 并复制给 userInvitationVo 返回
        Long userId = userInvitationEntity.getUserId();
        UserEntity user = userDao.selectOne(new QueryWrapper<UserEntity>()
                .eq("user_id", userId));
        UserInvitationVo userInvitationVo = new UserInvitationVo();
//        System.out.println("user信息:" + user);
        BeanUtils.copyProperties(user, userInvitationVo);
//        System.out.println("查询到的User:" + userInvitationVo);

        return userInvitationVo;
    }


    public List<UserInvitationVo> getUserAndPost() {
        List<UserInvitationVo> userInvitationForms = userInvitationDao.getUserAndPost();
        return userInvitationForms;
    }

}