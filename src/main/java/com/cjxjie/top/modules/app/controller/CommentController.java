package com.cjxjie.top.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjxjie.top.modules.app.entity.CommentEntity;
import com.cjxjie.top.modules.app.service.CommentService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;


/**
 * 评论信息表

 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@Api(tags = "评论信息表")
@RestController
@RequestMapping("app/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 列表
     */
    @ApiOperation("获取所有评论信息表列表")
    @RequestMapping("/list")
    @RequiresPermissions("app:comment:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = commentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("根据commentId获取信息")
    @RequestMapping("/info/{commentId}")
    @RequiresPermissions("app:comment:info")
    public R info(@PathVariable("commentId") Long commentId) {
            CommentEntity comment = commentService.getById(commentId);

        return R.ok().put("comment", comment);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping("/save")
    @RequiresPermissions("app:comment:save")
    public R save(@RequestBody CommentEntity comment) {
            commentService.save(comment);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping("/update")
    @RequiresPermissions("app:comment:update")
    public R update(@RequestBody CommentEntity comment) {
            commentService.updateById(comment);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("app:comment:delete")
    public R delete(@RequestBody Long[] commentIds) {
            commentService.removeByIds(Arrays.asList(commentIds));

        return R.ok();
    }

}
