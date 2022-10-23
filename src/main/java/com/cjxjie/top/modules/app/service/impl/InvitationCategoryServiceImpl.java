package com.cjxjie.top.modules.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.app.dao.InvitationCategoryDao;
import com.cjxjie.top.modules.app.entity.InvitationCategoryEntity;
import com.cjxjie.top.modules.app.service.InvitationCategoryService;


@Service("invitationCategoryService")
@DS(value = "app")
public class InvitationCategoryServiceImpl extends ServiceImpl<InvitationCategoryDao, InvitationCategoryEntity> implements InvitationCategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InvitationCategoryEntity> page = this.page(
                new Query<InvitationCategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}