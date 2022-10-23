package com.cjxjie.top.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.sys.dao.SysLogDao;
import com.cjxjie.top.modules.sys.entity.SysLogEntity;
import com.cjxjie.top.modules.sys.service.SysLogService;


@Service("sysLogService")
@DS("admin")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        IPage<SysLogEntity> page = this.page(
                new Query<SysLogEntity>().getPage(params),
                new QueryWrapper<SysLogEntity>()
                        .like(StringUtils.isNotBlank(key), "username", key)
        );

        return new PageUtils(page);
    }

}