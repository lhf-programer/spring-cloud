package com.lvhaifeng.cloud.common.exception;

import com.lvhaifeng.cloud.common.error.ErrorCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description 公共的业务异常
 * @Author haifeng.lv
 * @Date 2019/12/16 17:43
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
