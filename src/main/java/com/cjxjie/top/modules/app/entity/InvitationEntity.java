package com.cjxjie.top.modules.app.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 帖子信息表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-18 14:47:45
 */
@Data
@TableName("invitation")
@ToString
@Accessors(chain = true)
public class InvitationEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 帖子ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invitationId;
    /**
     * 标题
     */
    private String title;
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 文件
     */
    private Byte[] document;
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
     * 状态【0: 不显示  1:显示】
     */
    private Integer state;
    /**
     * 发帖地址
     */
    private String address;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 是否删除【0:不删除  1:删除】
     */
    private Integer isDelete;

    /**
     * 封面
     */
    private String cover;

}
