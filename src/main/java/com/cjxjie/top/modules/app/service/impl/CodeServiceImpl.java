package com.cjxjie.top.modules.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.modules.app.dao.CodeDao;
import com.cjxjie.top.modules.app.entity.CodeEntity;
import com.cjxjie.top.modules.app.service.CodeService;
import org.springframework.stereotype.Service;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/22 15:36
 */
@DS("app")
@Service("codeService")
public class CodeServiceImpl extends ServiceImpl<CodeDao, CodeEntity> implements CodeService {

    /**
     * 验证验证码是否有效
     *
     * @param code 验证码
     */
    @Override
    public Boolean verifiCode(String code, String phone) {
        // 查询该号码是否存在验证码
        CodeEntity codeEntity = this.getOne(new QueryWrapper<CodeEntity>()
                .eq("phone", phone));

        if (codeEntity == null) {
            return false;
        }


        // 并且时间有效
        return codeEntity.getCode().equals(code) && codeEntity.getOverTime().getTime() >= System.currentTimeMillis();
    }
}
