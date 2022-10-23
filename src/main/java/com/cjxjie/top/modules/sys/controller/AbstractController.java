package com.cjxjie.top.modules.sys.controller;

import com.cjxjie.top.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/08 14:12
 * <p>
 * 公共抽象控制器
 */
public abstract class AbstractController {
    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }
}
