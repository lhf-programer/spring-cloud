package com.lvhaifeng.cloud.common.exception;

import com.lvhaifeng.cloud.common.exception.auth.ClientTokenException;
import com.lvhaifeng.cloud.common.exception.auth.NonLoginException;
import com.lvhaifeng.cloud.common.exception.auth.UserInvalidException;
import com.lvhaifeng.cloud.common.exception.base.BaseException;
import com.lvhaifeng.cloud.common.exception.base.BizInvalidResponseException;
import com.lvhaifeng.cloud.common.msg.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常拦截处理器
 *
 * @author haifeng.lv
 * @version 2018/9/8
 */
@ControllerAdvice("com.lvhaifeng.cloud")
@ResponseBody
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public BaseResponse baseExceptionHandler(HttpServletResponse response, BaseException ex) {
        logger.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse otherExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error(ex.getMessage(), ex);
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(ClientTokenException.class)
    public BaseResponse clientTokenExceptionHandler(HttpServletResponse response, ClientTokenException ex) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        logger.error(ex.getMessage(), ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(NonLoginException.class)
    public BaseResponse userTokenExceptionHandler(HttpServletResponse response, NonLoginException ex) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        logger.error(ex.getMessage(), ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(UserInvalidException.class)
    public BaseResponse userInvalidExceptionHandler(HttpServletResponse response, UserInvalidException ex) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        logger.error(ex.getMessage(), ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(BizInvalidResponseException.class)
    public BaseResponse invalidResponseExceptionHandler(HttpServletResponse response, BizInvalidResponseException ex) {
        response.setStatus(HttpStatus.OK.value());
        logger.warn(ex.getMessage(), ex);
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ex.getStatus());
    }
}
