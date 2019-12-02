package com.lvhaifeng.cloud.common.exception.auth;


import com.lvhaifeng.cloud.common.constant.RestCodeConstants;
import com.lvhaifeng.cloud.common.exception.base.BaseException;

public class NonLoginException extends BaseException {
    public NonLoginException(String message) {
        super(message, RestCodeConstants.EX_USER_INVALID_CODE);
    }
}
