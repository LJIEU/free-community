package com.cjxjie.top.modules.app.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.app.dao.CategoryDao;
import com.cjxjie.top.modules.app.entity.CategoryEntity;
import com.cjxjie.top.modules.app.service.CategoryService;

@Slf4j
@Service("categoryService")
@DS(value = "app")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        String categoryId = (String) params.get("categoryId");
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .eq(StringUtils.isNotBlank(categoryId), "category_id", categoryId)
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> treeCategory() {
        List<CategoryEntity> list = categoryDao.selectList(new QueryWrapper<>());
        List<CategoryEntity> parent = list.stream()
                .filter(v -> v.getParentId() == 0)
                .sorted(Comparator.comparingInt(CategoryEntity::getSort))
                .collect(Collectors.toList());
        parent.forEach(v -> getChildes(v, list));

        return parent;
    }

    @Override
    public List<Long> getCategoryPath(Long categoryId) {
        Stack<Long> stack = new Stack<>();
        if (getPath(treeCategory(), categoryId, stack)) {
            return new ArrayList<>(stack);
        }
        return null;
    }

    @Override
    public List<String> nameList() {
        return baseMapper.nameList();
    }

    public boolean getPath(List<CategoryEntity> src, Long id, Stack<Long> stack) {
        Iterator<CategoryEntity> iterator = src.stream().iterator();
        while (iterator.hasNext()) {
            CategoryEntity next = iterator.next();
            if (Objects.equals(next.getCategoryId(), id)) {
                stack.push(next.getCategoryId());
//                System.out.println(next.getName() + "\t" + next.getCategoryId());
                return true;
            } else if (next.getChildes() != null) {
                stack.push(next.getCategoryId());
                if (!getPath(next.getChildes(), id, stack)) { // 如果子类未查询到则出栈
                    stack.pop();
//                    System.out.print(stack.pop() + "出栈\t");
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public void getChildes(CategoryEntity parent, List<CategoryEntity> src) {
        List<CategoryEntity> select = src.stream()
                .filter(v -> Objects.equals(v.getParentId(), parent.getCategoryId()))
                .sorted(Comparator.comparingInt(CategoryEntity::getSort))
                .collect(Collectors.toList());
        if (select.size() != 0)
            parent.setChildes(select); //  赋值子类
        else
            parent.setChildes(null);
        select.forEach(v -> { // 查询子类是否还有子类【递归】
            getChildes(v, src);
        });
    }

}