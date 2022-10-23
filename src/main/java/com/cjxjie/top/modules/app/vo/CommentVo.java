package com.cjxjie.top.modules.app.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/13 15:45
 */
@Data
@ToString
public class CommentVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long articleId;
    private String content;
    private Boolean isParent;
    private Long replyId;
    private String ip;
    private String city;

}
