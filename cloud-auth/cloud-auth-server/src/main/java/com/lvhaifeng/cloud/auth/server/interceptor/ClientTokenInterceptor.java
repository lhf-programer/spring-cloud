package com.lvhaifeng.cloud.auth.server.interceptor;

import com.lvhaifeng.cloud.auth.server.configuration.ClientConfiguration;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientTokenInterceptor implements RequestInterceptor {
    @Autowired
    private ClientConfiguration clientConfiguration;
    @Autowired
    private IAuthClientService authClientService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            requestTemplate.header(clientConfiguration.getClientTokenHeader(), authClientService.apply(clientConfiguration.getClientId(), clientConfiguration.getClientSecret()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
