package com.cjxjie.top.modules.oss.service.impl;

import com.cjxjie.top.modules.oss.dao.SysOssDao;
import com.cjxjie.top.modules.oss.entity.SysOssEntity;
import com.cjxjie.top.modules.oss.service.SysOssService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;



@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysOssEntity> page = this.page(
                new Query<SysOssEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}