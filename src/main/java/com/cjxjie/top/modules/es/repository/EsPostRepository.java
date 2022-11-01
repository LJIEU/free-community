package com.cjxjie.top.modules.es.repository;

import com.cjxjie.top.modules.es.docment.ESUserAndPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/30 10:02
 */
public interface EsPostRepository extends ElasticsearchRepository<ESUserAndPost, Long> {

    /**
     * 搜索查询
     *
     * @param userName 用户名
     * @param title    帖子标题
     * @param keywords 关键字
     * @param page     分页
    findUserNameOrPostTitleOrKeywords
     */
    Page<ESUserAndPost> findESUserAndPostByUsername(String userName, String title, String keywords, Pageable page);
}
