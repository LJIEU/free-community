package com.cjxjie.top.modules.app.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * 帖子分类
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-18 14:47:45
 */
@Data
@TableName("category")
public class CategoryEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<CategoryEntity> childes = new ArrayList<>();

    /**
     * 分类ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;
    /**
     * 父分类ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标URL
     */
    private String icon;
    /**
     * 状态【0:不显示  1:显示】
     */
    private Integer state;
    /**
     * 是否删除【0:否  1:删除】
     */
    @TableLogic
    private Integer isDelete;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}
