package com.cjxjie.top.modules.es.docment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/29 12:00
 */
@Data
public class ESPost implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 帖子ID
     */
    private Long invitationId;

    /**
     * 标题
     */
    @Field(type = FieldType.Keyword)
    private String title;

    /**
     * 帖子内容
     */
    // 中文分词
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String content;


    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String documentTranslate;

    /**
     * 浏览量
     */
    private Long pageviews;

    /**
     * 评论数
     */
    private Long comments;

    /**
     * 点赞数
     */
    private Long likes;

    /**
     * 话题
     */
    private String topic;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 发帖地址
     */
    private String address;

    /**
     * 封面
     */
    private String cover;


}
