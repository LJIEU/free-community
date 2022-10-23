package com.cjxjie.top.modules.oss.service;

import java.io.InputStream;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/12 13:58
 */
public interface FileService {
    String upload(InputStream inputStream, String module, String originalFilename);

}
