package com.lvhaifeng.cloud.common.exception.auth;


import com.lvhaifeng.cloud.common.constant.RestCodeConstants;
import com.lvhaifeng.cloud.common.exception.base.BaseException;

public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, RestCodeConstants.EX_CLIENT_FORBIDDEN_CODE);
    }

}
