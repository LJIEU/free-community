package com.cjxjie.top.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.service.UserService;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;


/**
 * 用户信息表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-18 14:47:45
 */
@Api(tags = "用户信息表")
@RestController
@RequestMapping("app/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据 用户名 获取用户信息
     */
    @GetMapping("/userInfo/{name}")
    public R getUserByName(@PathVariable(value = "name") String name){
        UserEntity user = userService.getByUsername(name);
        return R.ok().put("userInfo",user);
    }

    /**
     * 列表
     */
    @ApiOperation("获取所有用户信息表列表")
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("根据userId获取信息")
    @RequestMapping("/info/{userId}")
    public R info(@PathVariable("userId") Long userId) {
            UserEntity user = userService.getById(userId);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user) {
            userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping("/update")
    public R update(@RequestBody UserEntity user) {
            userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] userIds) {
            userService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
