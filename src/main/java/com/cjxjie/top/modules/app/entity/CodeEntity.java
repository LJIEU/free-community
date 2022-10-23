package com.cjxjie.top.modules.app.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/22 15:34
 */
@TableName("code")
@Data
public class CodeEntity {
    private String code;

    // 一个号码只能有一个验证码
    private String phone;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Date overTime;
}
