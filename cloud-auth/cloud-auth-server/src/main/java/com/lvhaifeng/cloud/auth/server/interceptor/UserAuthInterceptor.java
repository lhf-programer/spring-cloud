package com.lvhaifeng.cloud.auth.server.interceptor;

import com.alibaba.fastjson.JSON;
import com.lvhaifeng.cloud.auth.server.configuration.UserConfiguration;
import com.lvhaifeng.cloud.auth.server.jwt.user.JwtTokenUtil;
import com.lvhaifeng.cloud.common.constant.RequestHeaderConstants;
import com.lvhaifeng.cloud.common.context.BaseContextHandler;
import com.lvhaifeng.cloud.common.exception.auth.NonLoginException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.msg.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author haifeng.lv
 * @version 2018/9/10
 */
@Slf4j
public class UserAuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserConfiguration userConfiguration;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(userConfiguration.getUserTokenHeader());
        if (StringUtils.isEmpty(token)) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(userConfiguration.getUserTokenHeader())) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        if (token != null && token.startsWith(RequestHeaderConstants.JWT_TOKEN_TYPE)) {
            token = token.substring(RequestHeaderConstants.JWT_TOKEN_TYPE.length());
        }
        try {
            IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(token);
            BaseContextHandler.setToken(token);
            BaseContextHandler.setUserName(infoFromToken.getName());
            BaseContextHandler.setUserID(infoFromToken.getId());
        } catch (NonLoginException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            log.error(ex.getMessage(), ex);
            response.setContentType("UTF-8");
            response.getOutputStream().println(JSON.toJSONString(new BaseResponse(ex.getStatus(), ex.getMessage())));
            return false;
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
