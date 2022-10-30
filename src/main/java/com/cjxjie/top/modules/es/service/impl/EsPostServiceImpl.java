package com.cjxjie.top.modules.es.service.impl;

import com.cjxjie.top.modules.app.service.InvitationService;
import com.cjxjie.top.modules.app.service.UserService;
import com.cjxjie.top.modules.es.docment.ESUserAndPost;
import com.cjxjie.top.modules.es.repository.EsPostRepository;
import com.cjxjie.top.modules.es.service.EsPostService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;

    @Override
    public int importAll() {
        // 查询出所有的 帖子信息
        List<ESUserAndPost> list = userService.getImportAllList();
        Iterable<ESUserAndPost> iterable = esPostRepository.saveAll(list);
        Iterator<ESUserAndPost> iterator = iterable.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }
}
