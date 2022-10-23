package com.cjxjie.top.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.modules.app.entity.CodeEntity;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/22 15:36
 */
public interface CodeService extends IService<CodeEntity> {
    Boolean verifiCode(String code,String phone);
}
