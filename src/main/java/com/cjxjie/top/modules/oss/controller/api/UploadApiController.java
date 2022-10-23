package com.cjxjie.top.modules.oss.controller.api;

import com.cjxjie.top.common.exception.CustomizeException;
import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.oss.service.FileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/12 13:56
 *
 * 上传文件到本地
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("app/api")
public class UploadApiController {

    @Autowired
    private FileService fileService;

    @ApiOperation("上传到本地")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file,
            @ApiParam(value = "模块", required = true) @RequestParam("module") String module
    ) {

        log.warn(file.getName());
        log.warn(module);
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uploadURL = fileService.upload(inputStream, module, originalFilename);
            return R.ok().message("文件上传成功").data("url", uploadURL);
        } catch (Exception e) {
            throw new CustomizeException("上传文件失败!");
        }
    }
}
