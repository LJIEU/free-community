package com.cjxjie.top.modules.es.controller;

import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.es.service.EsPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/30 10:11
 */
@RestController
@RequestMapping("/es/esPost")
public class EsPostController {

    @Autowired
    private EsPostService esPostService;

    /**
     * 将数据库的所有帖子导入Elasticsearch中
     */
    @GetMapping("/importAll")
    public R importAll() {
        int count = esPostService.importAll();
        return R.ok().message("已插入数据:" + count + "条");
    }
}
