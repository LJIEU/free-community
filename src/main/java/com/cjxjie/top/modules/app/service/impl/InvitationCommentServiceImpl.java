package com.cjxjie.top.modules.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.app.dao.InvitationCommentDao;
import com.cjxjie.top.modules.app.entity.InvitationCommentEntity;
import com.cjxjie.top.modules.app.service.InvitationCommentService;


@Service("invitationCommentService")
@DS(value = "app")
public class InvitationCommentServiceImpl extends ServiceImpl<InvitationCommentDao, InvitationCommentEntity> implements InvitationCommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InvitationCommentEntity> page = this.page(
                new Query<InvitationCommentEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}