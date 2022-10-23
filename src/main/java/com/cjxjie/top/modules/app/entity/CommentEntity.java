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
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.List;

/**
 * 评论信息表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-18 14:47:45
 */
@Accessors(chain = true)
@Data
@TableName("comment")
public class CommentEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<CommentEntity> childList;

    /**
     * 评论ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;
    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞量
     */
    private Integer likes;

    /**
     * ip
     */
    private String ip;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态【0:不显示  1:显示】
     */
    private Integer state;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 是否删除【0:不删除  1:删除】
     */
    private Integer isDelete;

}
