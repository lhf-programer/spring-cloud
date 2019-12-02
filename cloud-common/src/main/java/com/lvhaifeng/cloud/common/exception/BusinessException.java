package com.lvhaifeng.cloud.common.exception;

import com.lvhaifeng.cloud.common.error.ErrorCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 公共的业务异常
 * time: 5/11/2019
 * @author haifeng.lv
 */
@ToString
@Getter
public class BusinessException extends RuntimeException {
    private final String errCode;
    private final String errMsg;

    public BusinessException(String errCode, String errMsg) {
        super("errCode: " + errCode + ", errMsg: " + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getErrCode(), errorCode.getErrMsg());
    }
}
