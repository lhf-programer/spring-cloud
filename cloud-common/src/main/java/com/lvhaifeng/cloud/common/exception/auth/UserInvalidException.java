package com.lvhaifeng.cloud.common.exception.auth;


import com.lvhaifeng.cloud.common.constant.RestCodeConstants;
import com.lvhaifeng.cloud.common.exception.base.BaseException;

public class UserInvalidException extends BaseException {
    public UserInvalidException(String message) {
        super(message, RestCodeConstants.EX_USER_PASS_INVALID_CODE);
    }
}
