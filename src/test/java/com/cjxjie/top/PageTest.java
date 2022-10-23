package com.cjxjie.top;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;
import com.cjxjie.top.modules.sys.entity.SysUserEntity;
import com.cjxjie.top.modules.sys.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/08 12:22
 */
@SpringBootTest
public class PageTest {

    @Resource
    private SysUserService sysUserService;

    @Test
    public void pageTest() {
        Map<String, Object> params = new HashMap<>();

        // 默认 第一页【curPage】 10条记录【limit】
        params.put("username", "root");
/*        Map<String, Object> map = sysUserService.getMap(
                new QueryWrapper<SysUserEntity>()
                        .like("username", "root")
                        *//*.eq("user_id", 2)*//*);

        System.out.println(map);*/

        PageUtils page = sysUserService.queryPage(params);
        System.out.println(page);
    }
}
