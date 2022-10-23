package com.cjxjie.top.modules.app.controller.admin;

import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.app.entity.CategoryEntity;
import com.cjxjie.top.modules.app.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/10 14:15
 */
@Api(tags = "帖子分类")
@RestController
@RequestMapping("app/admin/category")
public class CategoryAdminController {

    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    /**
     * 树结构
     */
    @GetMapping("/treeCategory")
    public R treeCategory() {
        List<CategoryEntity> list = categoryService.treeCategory();
        return R.ok().put("list", list);
    }

    /**
     * 查询路径
     */
    @GetMapping("/getCategoryPath/{categoryId}")
    @RequiresPermissions("app:category:all")
    public R getCategoryPath(@PathVariable(value = "categoryId") Long categoryId) {
        List<Long> path = categoryService.getCategoryPath(categoryId);
        // TODO 2022/10/5/10:34 需要将Long类型 转为 String
        return R.ok().put("path", path);
    }

    /**
     * 列表
     */
    @ApiOperation("获取所有帖子分类列表")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("根据categoryId获取信息")
    @GetMapping("/info/{categoryId}")
    @RequiresPermissions("app:category:all")
    public R info(@PathVariable("categoryId") Long categoryId) {
        CategoryEntity category = categoryService.getById(categoryId);
        return R.ok().put("category", category);
    }


//    修改删除都需要权限

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @RequiresPermissions("app:category:all")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @RequiresPermissions("app:category:all")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("app:category:all")
    public R delete(@RequestBody Long[] categoryIds) {
        categoryService.removeByIds(Arrays.asList(categoryIds));

        return R.ok();
    }


}
