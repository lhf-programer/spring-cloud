package com.lvhaifeng.cloud.auth.client.interceptor;

import com.alibaba.fastjson.JSON;
import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.client.annotation.IgnoreClientToken;
import com.lvhaifeng.cloud.auth.client.config.ServiceAuthConfig;
import com.lvhaifeng.cloud.auth.client.jwt.ServiceAuthUtil;
import com.lvhaifeng.cloud.common.exception.auth.ClientTokenException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * 服务token认证
 * @author haifeng.lv
 * @version 2018/9/12
 */
@SuppressWarnings("ALL")
public class ServiceAuthRestInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(ServiceAuthRestInterceptor.class);

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (HttpMethod.OPTIONS.matches(method)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CheckClientToken annotation = handlerMethod.getBeanType().getAnnotation(CheckClientToken.class);
        // 配置该注解，说明不进行服务拦截
        IgnoreClientToken ignoreClientToken = handlerMethod.getMethodAnnotation(IgnoreClientToken.class);
        if (annotation == null) {
            annotation = handlerMethod.getMethodAnnotation(CheckClientToken.class);
        }
        if (annotation == null || ignoreClientToken != null) {
            return super.preHandle(request, response, handler);
        } else {
            // 获取头部 token
            String token = request.getHeader(serviceAuthConfig.getTokenHeader());
            try {
                IJWTInfo infoFromToken = serviceAuthUtil.getInfoFromToken(token);
                // 获取属性，检测异常
                String uniqueName = infoFromToken.getUniqueName();
                return super.preHandle(request, response, handler);
            } catch (ClientTokenException ex) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                logger.error(ex.getMessage(), ex);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println(JSON.toJSONString(new Result(ex.getMessage(), ex.getStatus())));
                return false;
            }
        }
    }
}