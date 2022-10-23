package com.cjxjie.top.modules.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.modules.app.dao.CommentGroupDao;
import com.cjxjie.top.modules.app.entity.CommentGroupEntity;
import com.cjxjie.top.modules.app.service.CommentGroupService;
import org.springframework.stereotype.Service;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/12 21:21
 */
@DS(value = "app")
@Service
public class CommentGroupServiceImpl extends ServiceImpl<CommentGroupDao, CommentGroupEntity> implements CommentGroupService {
}
