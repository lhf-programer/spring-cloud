package com.lvhaifeng.cloud.common.exception;

import com.lvhaifeng.cloud.common.exception.auth.ClientTokenException;
import com.lvhaifeng.cloud.common.exception.auth.NonLoginException;
import com.lvhaifeng.cloud.common.exception.auth.UserInvalidException;
import com.lvhaifeng.cloud.common.exception.base.BaseException;
import com.lvhaifeng.cloud.common.exception.base.BizInvalidResponseException;
import com.lvhaifeng.cloud.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常拦截处理器
 * @author haifeng.lv
 * @version 2018/9/8
 */
@ControllerAdvice("com.lvhaifeng.cloud")
@ResponseBody
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public Result baseExceptionHandler(HttpServletResponse response, BaseException ex) {
        logger.error(ex.getMessage(), ex);
        Result result = new Result(ex.getMessage(), ex.getStatus());
        result.setSuccess(false);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }

    @ExceptionHandler(Exception.class)
    public Result otherExceptionHandler(HttpServletResponse response, Exception ex) {
        logger.error(ex.getMessage(), ex);
        Result result = new Result(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setSuccess(false);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }

    @ExceptionHandler(ClientTokenException.class)
    public Result clientTokenExceptionHandler(HttpServletResponse response, ClientTokenException ex) {
        logger.error(ex.getMessage(), ex);
        Result result = new Result(ex.getMessage(), ex.getStatus());
        result.setSuccess(false);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return result;
    }

    @ExceptionHandler(NonLoginException.class)
    public Result userTokenExceptionHandler(HttpServletResponse response, NonLoginException ex) {
        logger.error(ex.getMessage(), ex);
        Result result = new Result(ex.getMessage(), ex.getStatus());
        result.setSuccess(false);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return result;
    }

    @ExceptionHandler(UserInvalidException.class)
    public Result userInvalidExceptionHandler(HttpServletResponse response, UserInvalidException ex) {
        logger.error(ex.getMessage(), ex);
        Result result = new Result(ex.getMessage(), ex.getStatus());
        result.setSuccess(false);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return result;
    }

    @ExceptionHandler(BizInvalidResponseException.class)
    public Result invalidResponseExceptionHandler(HttpServletResponse response, BizInvalidResponseException ex) {
        logger.error(ex.getMessage(), ex);
        Result result = new Result(ex.getMessage(), ex.getStatus());
        result.setSuccess(false);
        response.setStatus(HttpStatus.OK.value());
        return result;
    }
}
