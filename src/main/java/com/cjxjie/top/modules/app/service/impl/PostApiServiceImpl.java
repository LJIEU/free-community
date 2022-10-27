package com.cjxjie.top.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjxjie.top.modules.app.dao.UserDao;
import com.cjxjie.top.modules.app.dao.UserInvitationDao;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.entity.UserInvitationEntity;
import com.cjxjie.top.modules.app.service.PostApiService;
import com.cjxjie.top.modules.app.utils.AppRedis;
import com.cjxjie.top.modules.app.vo.UserInvitationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/06 10:50
 */
@Slf4j
@Service
public class PostApiServiceImpl implements PostApiService {

    @Lazy
    @Autowired
    private UserInvitationDao userInvitationDao;
    @Lazy
    @Autowired
    private UserDao userDao;


    @Override
    public List<UserInvitationVo> getUserAndPostInfo(List<InvitationEntity> postList) {
        List<UserInvitationVo> userInvitationVoList = postList.stream().map(v -> {
            UserInvitationVo userInvitationVo = getUser(v.getInvitationId());
            // 如果文件不为null 转换
            if (v.getDocument() != null) {
                // 读取
                int i = 0;
                byte[] bytes = new byte[v.getDocument().length];
                for (Byte aByte : v.getDocument()) {
                    bytes[i++] = aByte;
                }
                String s1 = new String(bytes);
                log.warn("读取信息:" + s1);
                userInvitationVo.setDocumentTranslate(s1);
            }
            if (userInvitationVo != null) {
                BeanUtils.copyProperties(v, userInvitationVo);
            }
            return userInvitationVo;
        }).collect(Collectors.toList());
        return userInvitationVoList;
    }


    @Override
    public UserEntity getPostUser(Long postId) {
        UserInvitationEntity userInvitationEntity = userInvitationDao.selectOne(new QueryWrapper<UserInvitationEntity>().eq("invitation_id", postId));
        Long userId = userInvitationEntity.getUserId();
        return userDao.selectOne(new QueryWrapper<UserEntity>().eq("user_id", userId));
    }

    public UserInvitationVo getUser(Long invitationId) {
        // 根据 帖子ID 搜索 用户
        UserInvitationEntity userInvitationEntity = userInvitationDao.selectOne(new QueryWrapper<UserInvitationEntity>()
                .eq("invitation_id", invitationId));

        if (userInvitationEntity == null) {
            return null;
        }

        // 查询用户信息 并复制给 userInvitationVo 返回
        Long userId = userInvitationEntity.getUserId();
        UserEntity user = userDao.selectOne(new QueryWrapper<UserEntity>()
                .eq("user_id", userId));
        UserInvitationVo userInvitationVo = new UserInvitationVo();
        BeanUtils.copyProperties(user, userInvitationVo);

        return userInvitationVo;
    }

}
