package com.lvhaifeng.cloud.common.error;

/**
 * 错误码
 * time: 5/13/2019
 *
 * @author haifeng.lv
 */
public final class ErrorCode {
    private final String errCode;
    private final String errMsg;

    public ErrorCode(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
