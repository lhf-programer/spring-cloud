package com.lvhaifeng.cloud.auth.client.interceptor;

import com.lvhaifeng.cloud.auth.client.config.ServiceAuthConfig;
import com.lvhaifeng.cloud.auth.client.config.UserAuthConfig;
import com.lvhaifeng.cloud.auth.client.jwt.ServiceAuthUtil;
import com.lvhaifeng.cloud.common.context.BaseContextHandler;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description Feign 请求拦截
 * @Author haifeng.lv
 * @Date 2019/12/16 17:31
 */
public class ServiceFeignInterceptor implements RequestInterceptor {
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;
    @Autowired
    private UserAuthConfig userAuthConfig;

    public ServiceFeignInterceptor() {
    }

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        requestTemplate.header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken());
    }

}
