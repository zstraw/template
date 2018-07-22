package com.didi.game.common;


public class AppBusinessException extends BaseException {

    //类似Http状态码
    private int httpStatus = 200;

    public AppBusinessException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public AppBusinessException(String message) {
        super(message);
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
