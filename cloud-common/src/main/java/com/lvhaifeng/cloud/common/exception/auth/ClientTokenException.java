package com.lvhaifeng.cloud.common.exception.auth;


import com.lvhaifeng.cloud.common.constant.RestCodeConstants;
import com.lvhaifeng.cloud.common.exception.base.BaseException;

public class ClientTokenException extends BaseException {
    public ClientTokenException(String message) {
        super(message, RestCodeConstants.EX_CLIENT_INVALID_CODE);
    }
}
