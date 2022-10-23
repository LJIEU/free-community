package com.cjxjie.top.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cjxjie.top.common.validator.group.AddGroup;
import com.cjxjie.top.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统用户
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-06 17:36:34
 */
@ToString
@Data
@TableName("sys_user")
@Accessors(chain = true)
public class SysUserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "修改必须指定ID", groups = {UpdateGroup.class})
    private Long userId;

    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Email(message = "邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    private List<Long> roleIdList;

    /**
     * 创建者ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    // 这个配置要自己写组件注入容器中才能生效 去实现 MetaObjectHandler 接口的 两个方法【insertFill、updateFill】
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
