package com.cjxjie.top.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.modules.app.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 帖子分类
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> treeCategory();

    List<Long> getCategoryPath(Long categoryId);

    List<String> nameList();

}

