package com.cjxjie.top.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/20 17:16
 */
@Data
@ToString
@TableName("comment_like")
public class CommentLikeEntity implements Serializable {
    private static final long serialVersionId = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long commentLikeId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

}
