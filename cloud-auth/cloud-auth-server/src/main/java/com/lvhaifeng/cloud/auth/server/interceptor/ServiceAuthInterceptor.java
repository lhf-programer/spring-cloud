package com.lvhaifeng.cloud.auth.server.interceptor;

import com.lvhaifeng.cloud.auth.server.configuration.ClientConfiguration;
import com.lvhaifeng.cloud.auth.server.jwt.client.ClientTokenUtil;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.AuthClientService;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import com.lvhaifeng.cloud.common.exception.auth.ClientForbiddenException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("ALL")
public class ServiceAuthInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(ServiceAuthInterceptor.class);

    @Autowired
    private ClientTokenUtil clientTokenUtil;
    @Autowired
    private IAuthClientService authClientService;
    @Autowired
    private ClientConfiguration clientConfiguration;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String token = request.getHeader(clientConfiguration.getClientTokenHeader());
        // 获取 jwt基本信息
        IJWTInfo infoFromToken = clientTokenUtil.getInfoFromToken(token);
        String uniqueName = infoFromToken.getUniqueName();
        // 获取所有允许的 client
        for(String client: authClientService.getAllowedClient(clientConfiguration.getClientId())){
            if(client.equals(uniqueName)){
                return super.preHandle(request, response, handler);
            }
        }
        throw new ClientForbiddenException("Client is Forbidden!");
    }
}
