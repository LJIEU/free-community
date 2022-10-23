package com.cjxjie.top.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/12 21:17
 */
@Data
@TableName("comment_group")
public class CommentGroupEntity implements Serializable {

    private static final long serialVersionId = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long commentGroupId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
}
