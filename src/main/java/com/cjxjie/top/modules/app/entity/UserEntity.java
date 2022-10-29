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
 * 用户信息表
 *
 * @author JIE
 * @email 2353471003@qq.com
 * @since 2022-09-18 14:47:45
 */
@Data
@Accessors(chain = true)
@ToString
@TableName("user")
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 盐
     */
    private String salt;
    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 性别【0: 男  1: 女  3: 未知】
     */
    private Integer sex;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 地址
     */
    private String address;
    /**
     * 职业
     */
    private String profession;
    /**
     * 封面url
     */
    private String cover;
    /**
     * 状态 【0: 下线  1: 在线】
     */
    private Integer state;
    /**
     * 是否删除【0: 显示  1:删除】
     */
    private Integer isDelete;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}
