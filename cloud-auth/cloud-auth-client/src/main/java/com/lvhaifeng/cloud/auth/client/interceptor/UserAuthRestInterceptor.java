package com.lvhaifeng.cloud.auth.client.interceptor;

import com.alibaba.fastjson.JSON;
import com.lvhaifeng.cloud.auth.client.annotation.CheckUserToken;
import com.lvhaifeng.cloud.auth.client.annotation.IgnoreUserToken;
import com.lvhaifeng.cloud.auth.client.config.UserAuthConfig;
import com.lvhaifeng.cloud.auth.client.jwt.UserAuthUtil;
import com.lvhaifeng.cloud.common.constant.RequestHeaderConstants;
import com.lvhaifeng.cloud.common.context.BaseContextHandler;
import com.lvhaifeng.cloud.common.exception.auth.NonLoginException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @Description 用户token拦截认证
 * @Author haifeng.lv
 * @Date 2019/12/16 17:32
 */
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(UserAuthRestInterceptor.class);

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (HttpMethod.OPTIONS.matches(method)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CheckUserToken annotation = handlerMethod.getBeanType().getAnnotation(CheckUserToken.class);
        // 配置该注解，说明不进行用户拦截
        IgnoreUserToken ignoreUserToken = handlerMethod.getMethodAnnotation(IgnoreUserToken.class);
        if (annotation == null) {
            annotation = handlerMethod.getMethodAnnotation(CheckUserToken.class);
        }
        if (annotation == null || ignoreUserToken != null) {
            return super.preHandle(request, response, handler);
        } else {
            // 获取用户 token
            String token = request.getHeader(userAuthConfig.getTokenHeader());
            // 如果为空则到 cookie中查找
            if (StringUtils.isEmpty(token)) {
                if (request.getCookies() != null) {
                    for (Cookie cookie : request.getCookies()) {
                        if (cookie.getName().equals(userAuthConfig.getTokenHeader())) {
                            token = cookie.getValue();
                        }
                    }
                }
            }

            // 截取 token
            if (token != null && token.startsWith(RequestHeaderConstants.JWT_TOKEN_TYPE)) {
                token = token.substring(RequestHeaderConstants.JWT_TOKEN_TYPE.length());
            }

            try {
                // 解析 token
                IJWTInfo infoFromToken = userAuthUtil.getInfoFromToken(token);
                BaseContextHandler.setToken(token);
                BaseContextHandler.setUserName(infoFromToken.getName());
                BaseContextHandler.setUserID(infoFromToken.getId());
            } catch (NonLoginException ex) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                logger.error(ex.getMessage(), ex);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println(JSON.toJSONString(new Result(ex.getMessage(), ex.getStatus())));
                return false;
            }

            return super.preHandle(request, response, handler);
        }
    }

    /**
     * @description 请求完成后对资源进行清理
     * @author haifeng.lv
     * @param: ex
     * @updateTime 2019/11/30 15:11
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
