package com.lvhaifeng.cloud.common.msg;

public class BaseResponse {
    private int status = 200;
    private String message;
    private Integer errorCode;

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse(int status, String message, Integer errorCode) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

    public BaseResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
