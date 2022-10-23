package com.cjxjie.top.modules.app.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/20 16:58
 */
@Data
@ToString
public class UserCommentVo {
    private String userName;
    private Long commentId;
    private Boolean whether;
}
