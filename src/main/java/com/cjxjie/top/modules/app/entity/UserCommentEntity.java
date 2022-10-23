package com.cjxjie.top.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户和评论关联表
 */
@TableName("user_comment")
@Data
public class UserCommentEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long userCommentId;


    private Long userId;

    private Long commentId;

    /**
     * 是否点赞【0: 未点赞  1:点赞】
     */
    private Integer isLike;

}
