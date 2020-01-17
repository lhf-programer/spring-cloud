package com.lvhaifeng.cloud.common.exception;

import com.lvhaifeng.cloud.common.exception.auth.ClientInvalidException;
import com.lvhaifeng.cloud.common.exception.auth.ClientTokenException;
import com.lvhaifeng.cloud.common.exception.auth.NonLoginException;
import com.lvhaifeng.cloud.common.exception.base.BaseException;
import com.lvhaifeng.cloud.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description 全局异常拦截处理器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:43
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

    @ExceptionHandler(BusinessException.class)
    public Result BusinessExceptionHandler(HttpServletResponse response, BusinessException ex) {
        logger.error(ex.getErrMsg(), ex);
        Result result = new Result(ex.getErrMsg(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setSuccess(false);
        response.setStatus(HttpStatus.OK.value());
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

    @ExceptionHandler(ClientInvalidException.class)
    public Result clientInvalidException(HttpServletResponse response, ClientInvalidException ex) {
        logger.error(ex.getMessage(), ex);
        Result result = new Result(ex.getMessage(), ex.getStatus());
        result.setSuccess(false);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return result;
    }

}
