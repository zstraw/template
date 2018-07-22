package com.didi.game.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Result {

    private int code;
    private Object message;
    private String errorInfo;

    public Result(Object message) {
        this(200, message, null);
    }

    @JsonCreator
    public Result(
            @JsonProperty("success") int code,
            @JsonProperty("message") Object message,
            @JsonProperty("errorInfo") String errorInfo) {
        this.code = code;
        this.message = message;
        this.errorInfo = errorInfo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
