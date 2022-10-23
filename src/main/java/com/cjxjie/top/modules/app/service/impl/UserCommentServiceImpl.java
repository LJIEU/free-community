package com.cjxjie.top.modules.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.modules.app.dao.UserCommentDao;
import com.cjxjie.top.modules.app.entity.UserCommentEntity;
import com.cjxjie.top.modules.app.service.UserCommentService;
import org.springframework.stereotype.Service;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/12 15:10
 */
@Service(value = "userCommentService")
@DS(value = "app")
public class UserCommentServiceImpl extends ServiceImpl<UserCommentDao, UserCommentEntity> implements UserCommentService {

}
