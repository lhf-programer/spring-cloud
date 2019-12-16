package com.lvhaifeng.cloud.common.exception.base;

import com.lvhaifeng.cloud.common.constant.RestCodeConstants;

public class BizInvalidResponseException extends BaseException {
    public BizInvalidResponseException(String message) {
        super(message, RestCodeConstants.EX_BUSINESS_BASE_CODE);
    }
    public BizInvalidResponseException(String message, Integer code) {
        super(message, code);
    }
}
