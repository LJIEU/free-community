package com.cjxjie.top.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.sys.oauth2.TokenGenerator;
import org.springframework.stereotype.Service;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cjxjie.top.modules.sys.dao.SysUserTokenDao;
import com.cjxjie.top.modules.sys.entity.SysUserTokenEntity;
import com.cjxjie.top.modules.sys.service.SysUserTokenService;


@Service("sysUserTokenService")
@DS("admin")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {

    private final static int EXPIRE = 3600 * 12;

    /**
     * 生成一个Token
     */
    @Override
    public R createToken(Long userId) {
        String token = TokenGenerator.generateValue();

        // 创建时的时间
        Date now = new Date();

        // 过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        // 判断Token是否生成过
        SysUserTokenEntity tokenEntity = this.getById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId).setToken(token).setUpdateTime(now).setExpireTime(expireTime);
            this.save(tokenEntity);
        } else {
            tokenEntity.setToken(token).setUpdateTime(now).setExpireTime(expireTime);

            // 更新
            this.updateById(tokenEntity);
        }
        return R.ok().put("token", token).put("expire", EXPIRE);
    }

    @Override
    public void logout(Long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }

}