package com.cjxjie.top.modules.sys.form;

import lombok.Data;
import lombok.ToString;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/07 10:14
 */
@ToString
@Data
public class PostForm {
    /**
     * HTML内容
     */
    private String html;

    /**
     * 内容
     */
    private String Content;

    /**
     * 描述
     */
    private String description;

    /**
     * 标题
     */
    private String title;

    /**
     * 话题
     */
    private String topic;
}
