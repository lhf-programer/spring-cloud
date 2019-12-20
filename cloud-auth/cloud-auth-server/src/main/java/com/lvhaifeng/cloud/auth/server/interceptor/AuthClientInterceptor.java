package com.lvhaifeng.cloud.auth.server.interceptor;

import com.lvhaifeng.cloud.auth.server.configuration.ClientConfiguration;
import com.lvhaifeng.cloud.auth.server.jwt.client.ClientTokenHelper;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import com.lvhaifeng.cloud.common.exception.auth.ClientInvalidException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 客户端拦截器
 * @Author haifeng.lv
 * @Date 2019/12/19 18:40
 */
public class AuthClientInterceptor implements HandlerInterceptor {
    @Autowired
    private ClientTokenHelper clientTokenHelper;
    @Autowired
    private ClientConfiguration clientConfiguration;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(clientConfiguration.getClientTokenHeader());
        // 验证客户端 token
        if (StringUtils.isNotBlank(token) && clientTokenHelper.verifyClientToken(token, clientConfiguration.getClientId())) {
            return true;
        }
        throw new ClientInvalidException("该客户端不被允许!");
    }
}
