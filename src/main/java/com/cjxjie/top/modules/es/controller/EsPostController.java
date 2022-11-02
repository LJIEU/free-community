package com.cjxjie.top.modules.es.controller;

import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.es.docment.ESPost;
import com.cjxjie.top.modules.es.service.EsPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/30 10:11
 */
@Api(value = "Elasticsearch模块")
@RestController
@RequestMapping("/es/esPost")
@Log4j
public class EsPostController {

    @Autowired
    private EsPostService esPostService;

    /**
     * 将数据库的所有帖子导入Elasticsearch中
     */
    @ApiOperation(value = "导入数据到ES中")
    @GetMapping("/importAll")
    public R importAll() {
        int count = esPostService.importAll();
        return R.ok().message("已插入数据:" + count + "条");
    }

    @ApiOperation("分页查询数据")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    public R search(@RequestParam(required = false) String keyword,
                    @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                    @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<ESPost> esProductPage = esPostService.search(keyword, pageNum, pageSize);
        List<ESPost> esProducts = esProductPage.getContent();
        esProducts.forEach(System.out::println);
        return R.ok().put("esProducts", esProducts);
    }
}
