package com.cjxjie.top.modules.app.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cjxjie.top.modules.app.entity.InvitationEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/05 17:24
 */
//@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class UserInvitationVo  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 职业
     */
    private String profession;


    private String documentTranslate;

    /**
     * 帖子ID
     */
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
