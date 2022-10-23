package com.cjxjie.top.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.app.dao.UserDao;
import com.cjxjie.top.modules.app.dao.UserInvitationDao;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.entity.UserInvitationEntity;
import com.cjxjie.top.modules.app.vo.UserInvitationVo;
import com.cjxjie.top.modules.app.service.InvitationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/05 17:34
 */
@SpringBootTest
public class UserInvitation {
    @Autowired
    private UserInvitationDao userInvitationDao;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private UserDao userDao;

    @Test
    public void getUserAndPost() {
/*        List<UserInvitationForm> userInvitationForms = userInvitationDao.getUserAndPost();
        System.out.println(userInvitationForms);*/
        HashMap<String, Object> params = new HashMap<>();
        PageUtils pageUtils = invitationService.queryPage(params);
        List<?> list = pageUtils.getList();
        List<UserInvitationVo> userInvitationVoList = list.stream().map(v -> {
            if (v instanceof InvitationEntity) {
                UserInvitationVo userInvitationVo = getUser(((InvitationEntity) v).getInvitationId());
                System.out.println("帖子信息:" + v);
                BeanUtils.copyProperties(v, userInvitationVo);
                return userInvitationVo;
            }
            return null;
        }).collect(Collectors.toList());
        System.out.println(userInvitationVoList);
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
        System.out.println("user信息:" + user);
        BeanUtils.copyProperties(user, userInvitationVo);
        System.out.println("查询到的User:" + userInvitationVo);

        return userInvitationVo;
    }
}
