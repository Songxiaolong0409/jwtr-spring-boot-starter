package com.framework.exception;

/**
 * 自定义 业务异常  带错误码异常,这个是用来给业务抛出带错误码异常的
 */
public class ApiCodeException extends Exception {

    private Integer errCode;
    private String errMsg;


    public ApiCodeException(Integer errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
