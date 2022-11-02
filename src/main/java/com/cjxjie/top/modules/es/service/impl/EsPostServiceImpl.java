package com.cjxjie.top.modules.es.service.impl;

import com.cjxjie.top.modules.app.service.InvitationService;
import com.cjxjie.top.modules.es.docment.ESPost;
import com.cjxjie.top.modules.es.repository.EsPostRepository;
import com.cjxjie.top.modules.es.service.EsPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/30 10:12
 */
@Service
public class EsPostServiceImpl implements EsPostService {

    @Autowired
    private EsPostRepository esPostRepository;

    @Autowired
    private InvitationService invitationService;

    @Override
    public int importAll() {
        // 查询出所有的 帖子信息
        List<ESPost> list = invitationService.getImportAllList();
        Iterable<ESPost> iterable = esPostRepository.saveAll(list);
        Iterator<ESPost> iterator = iterable.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }

    @Override
    public Page<ESPost> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        System.out.println(keyword + "\t" + pageNum + "\t" + pageSize);


        return esPostRepository.findESPostByTitleOrContent(keyword, keyword, pageable);
    }
}
