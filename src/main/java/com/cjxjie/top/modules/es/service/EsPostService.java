package com.cjxjie.top.modules.es.service;

import com.cjxjie.top.modules.es.docment.ESUserAndPost;
import org.springframework.data.domain.Page;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/30 10:12
 */
public interface EsPostService {
    int importAll();

    Page<ESUserAndPost> search(String keyword, Integer pageNum, Integer pageSize);

}
