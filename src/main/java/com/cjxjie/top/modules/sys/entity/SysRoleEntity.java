package com.cjxjie.top.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

/**
 * 角色
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-06 17:36:34
 */
@Data
@TableName("sys_role")
@ToString
public class SysRoleEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    // 因为自动生成的ID号长度为19位 前端会丢失数据 所以在发送Json数据时ID转 String 类型
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建者ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUserId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 菜单ID
     */
    @TableField(exist = false)
    private List<Long> menuIdList = new ArrayList<>();

    /**
     * 前端查看的菜单ID
     */
    @TableField(exist = false)
    private List<String> tempMenuIdList = new ArrayList<>();
}
