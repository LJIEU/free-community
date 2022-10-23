package com.cjxjie.top.modules.oss.service.impl;

import com.cjxjie.top.modules.oss.service.FileService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/12 13:58
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${picture.up-path}")
    private String upPath;

    /***
     *
     * @param inputStream       输入流
     * @param module        模块
     * @param originalFilename   原始文件名
     */
    @SneakyThrows
    @Override
    public String upload(InputStream inputStream, String module, String originalFilename) {
        log.warn("输入流: " + inputStream);
        log.warn("模块: " + module);
        log.warn("原始文件名: " + originalFilename);

//        File result = new File("E:/IDEA项目练习/ParentBook/图片/" + module + "/" + originalFilename);
        File result = new File(upPath + module + "/" + originalFilename);
        // 如果文件不存在 就创建文件
        if (!result.exists()) {
            result.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(result);
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, len);
        }

        fileOutputStream.close();
        inputStream.close();
//        return "http://localhost:8888/show/articles/" + originalFilename;
        return "http://localhost:8888/show/" + originalFilename;
    }

}
