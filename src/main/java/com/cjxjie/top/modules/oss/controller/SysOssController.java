package com.cjxjie.top.modules.oss.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import com.cjxjie.top.common.exception.CustomizeException;
import com.cjxjie.top.common.utils.Constant;
import com.cjxjie.top.common.validator.ValidatorUtils;
import com.cjxjie.top.modules.oss.cloud.CloudStorageConfig;
import com.cjxjie.top.modules.oss.cloud.ConfigConstant;
import com.cjxjie.top.modules.oss.cloud.OSSFactory;
import com.cjxjie.top.modules.oss.entity.SysOssEntity;
import com.cjxjie.top.modules.oss.service.SysOssService;
import com.cjxjie.top.modules.sys.service.SysConfigService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件上传
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @date 2022-09-11 15:10:37
 */
@Api(tags = "文件上传")
@Slf4j
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
    @Autowired
    private SysOssService sysOssService;

    @Autowired
    private SysConfigService sysConfigService;

    // 云存储密钥
    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

    /**
     * 列表
     */
    @ApiOperation("获取所有文件上传列表")
    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysOssService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 配置信息
     */
    @GetMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public R config(){
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);
        return R.ok().put("config", config);

    }

    /**
     * 保存云存储配置信息
     */
    @PostMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    public R saveConfig(@RequestBody CloudStorageConfig config) {
//        log.warn("配置存储:"+config);
        //校验类型
        ValidatorUtils.validateEntity(config);
        ValidatorUtils.validateEntity(config, Constant.CloudService.getByValue(config.getType()));

//        log.warn("校验完成!");
        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

        return R.ok();
    }


    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new CustomizeException("上传文件不能为空");
        }

        //上传文件
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        String url = Objects.requireNonNull(OSSFactory.build()).uploadSuffix(file.getBytes(), suffix);

        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        sysOssService.save(ossEntity);

        return R.ok().put("url", url);
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    public R delete(@RequestBody Long[] ids){
        sysOssService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
