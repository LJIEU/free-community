package com.cjxjie.top.modules.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.app.dao.UserInvitationDao;
import com.cjxjie.top.modules.app.entity.UserInvitationEntity;
import com.cjxjie.top.modules.app.service.UserInvitationService;


@Service("userInvitationService")
@DS(value = "app")
public class UserInvitationServiceImpl extends ServiceImpl<UserInvitationDao, UserInvitationEntity> implements UserInvitationService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserInvitationEntity> page = this.page(
                new Query<UserInvitationEntity>().getPage(params),
                new QueryWrapper<>()
        );
        return new PageUtils(page);
    }

}