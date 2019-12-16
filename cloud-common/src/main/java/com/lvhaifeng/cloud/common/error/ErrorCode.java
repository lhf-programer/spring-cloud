package com.lvhaifeng.cloud.common.error;

/**
 * @Description 错误码
 * @Author haifeng.lv
 * @Date 2019/12/16 17:42
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
