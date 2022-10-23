package com.cjxjie.top.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.sys.entity.SysUserTokenEntity;

/**
 * 系统用户Token
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-06 17:36:34
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

//    PageUtils queryPage(Map<String, Object> params);

    R createToken(Long userId);

    void logout(Long userId);
}

