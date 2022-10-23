package com.cjxjie.top.modules.app.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/15 11:14
 */
@Accessors(chain = true)
@Data
public class CommentAndUserVo{

    /**
     * 用户基本信息
     */
    private UserInfoVo author;

    /**
     * 评论数量
     */
    private Long commentNum;

    /**
     * 子评论数量c
     */
    private Long childCommentCount;

    private List<CommentAndUserVo> childList;

    /**
     * 评论ID
     */
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;

    /**
     * 地址
     */
    private String address;


    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞量
     */
    private Integer likes;
    /**
     * 状态【0:不显示  1:显示】
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否删除【0:不删除  1:删除】
     */
    private Integer isDelete;


    private Integer isLike;


}
