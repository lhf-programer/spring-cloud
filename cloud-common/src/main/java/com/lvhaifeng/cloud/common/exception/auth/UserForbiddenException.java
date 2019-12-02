package com.lvhaifeng.cloud.common.exception.auth;


import com.lvhaifeng.cloud.common.constant.RestCodeConstants;
import com.lvhaifeng.cloud.common.exception.base.BaseException;

public class UserForbiddenException extends BaseException {
    public UserForbiddenException(String message) {
        super(message, RestCodeConstants.EX_USER_FORBIDDEN_CODE);
    }

}
