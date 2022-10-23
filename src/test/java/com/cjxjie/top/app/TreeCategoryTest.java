package com.cjxjie.top.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjxjie.top.modules.app.dao.CategoryDao;
import com.cjxjie.top.modules.app.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.Query;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/04 18:17
 */
@SpringBootTest
public class TreeCategoryTest {
    @Autowired
    CategoryDao categoryDao;

    @Test
    public void test() {
        List<CategoryEntity> list = categoryDao.selectList(new QueryWrapper<CategoryEntity>());
        List<CategoryEntity> parent = list.stream().filter(v -> v.getParentId() == 0).collect(Collectors.toList());

        System.out.println("====================== 原 始 数 据 ======================");
        list.forEach(System.out::println);

        System.out.println("====================== 父 类 数 据 ======================");
        parent.forEach(System.out::println);

        parent.forEach(v -> {
            Stack<String> stack = new Stack<>();
            getChildes(v, list);
        });

        System.out.println("====================== 之 后 父 类 数 据 ======================");
        parent.forEach(System.out::println);

        System.out.println("====================== 寻 找 路 径 ======================");
        Stack<String> stack = new Stack<>();
        getPath(parent, 10L, stack);
        System.out.println(stack);
    }

    public void getChildes(CategoryEntity parent, List<CategoryEntity> src) {
        List<CategoryEntity> select = src.stream()
                .filter(v -> Objects.equals(v.getParentId(), parent.getCategoryId()))
                .sorted((v1, v2) -> {
                    return v1.getSort() - v2.getSort(); // 递增
                })
                .collect(Collectors.toList());
        System.out.println("====================== " + parent.getName() + " 子 类 ======================");
        select.forEach(System.out::println);
        parent.setChildes(select); //  赋值
        select.forEach(v -> {
            getChildes(v, src);
        });
    }

    public boolean getPath(List<CategoryEntity> src, Long id, Stack<String> stack) {
        Iterator<CategoryEntity> iterator = src.stream().iterator();
        while (iterator.hasNext()) {
            CategoryEntity next = iterator.next();
            if (Objects.equals(next.getCategoryId(), id)) {
                stack.push(next.getName());
                System.out.println(next.getName() + "\t" + next.getCategoryId());
                return true;
            } else if (next.getChildes() != null) {
                stack.push(next.getName());
                if (!getPath(next.getChildes(), id, stack)) { // 如果子类未查询到则出栈
                    System.out.print(stack.pop() + "出栈\t");
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}
