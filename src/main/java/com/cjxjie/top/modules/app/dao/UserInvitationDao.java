package com.cjxjie.top.modules.app.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.modules.app.entity.UserInvitationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjxjie.top.modules.app.vo.UserInvitationVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户与帖子关联表
 * 
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@Mapper
@DS(value = "app")
public interface UserInvitationDao extends BaseMapper<UserInvitationEntity> {

    List<UserInvitationVo> getUserAndPost();

}
