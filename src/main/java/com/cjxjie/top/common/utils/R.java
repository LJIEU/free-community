package com.cjxjie.top.common.utils;


import cn.hutool.http.HttpStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/09/06 11:16
 * <p>
 * 返回数据结果
 */
@SuppressWarnings("all")
@Data
public class R<T> /*extends HashMap<String, Object>*/ implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    public R() {
    }

    public static R<Object> ok() {
        R<Object> r = new R<>();
        r.setCode(HttpStatus.HTTP_OK);
        r.setMessage("成功");
        return r;
    }

    public static R<Object> error() {
        R<Object> r = new R<>();
        r.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        r.setMessage("未知错误!");
        return r;
    }

    public static R<Object> setResult(Integer code, String message) {
        R<Object> r = new R<>();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static R error(Integer code, String message) {
        R<Object> r = new R<>();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static R ok(Integer code, String message) {
        R<Object> r = new R<>();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public R<T> data(String key, Object obj) {
        this.data.put(key, obj);
        return this;
    }

    public R<T> put(String key, Object obj) {
        return data(key, obj);
    }

    public R<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public R<T> code(Integer code) {
        this.setCode(code);
        return this;
    }
}
