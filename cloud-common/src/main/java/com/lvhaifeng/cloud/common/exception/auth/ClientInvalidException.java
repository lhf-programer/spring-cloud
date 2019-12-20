package com.lvhaifeng.cloud.common.exception.auth;

import com.lvhaifeng.cloud.common.constant.RestCodeConstants;
import com.lvhaifeng.cloud.common.exception.base.BaseException;

/**
 * @Description 验证客户端错误
 * @Author haifeng.lv
 * @Date 2019/12/19 18:18
 */
public class ClientInvalidException extends BaseException {
    public ClientInvalidException(String message) {
        super(message, RestCodeConstants.EX_CLIENT_INVALID_CODE);
    }
}