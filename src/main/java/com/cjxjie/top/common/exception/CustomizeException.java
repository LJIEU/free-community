package com.cjxjie.top.common.exception;

import java.io.Serial;

/**
 * @author 刘杰
 * @version 1.0
 * @Date: 2022/7/27 8:16
 */
public class CustomizeException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private int code = 500;

    public CustomizeException(String message) {
        super(message);
        this.message = message;
    }

    public CustomizeException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public CustomizeException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public CustomizeException(String message, int code, Throwable e) {
        super(message, e);
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
