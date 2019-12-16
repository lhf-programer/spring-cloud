package com.lvhaifeng.cloud.auth.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 客户端配置信息
 * @Author haifeng.lv
 * @Date 2019/12/16 17:33
 */
@Configuration
public class ClientConfiguration {
    @Value("${client.id}")
    private String clientId;
    @Value("${client.secret}")
    private String clientSecret;
    @Value("${client.token-header}")
    private String clientTokenHeader;

    public String getClientTokenHeader() {
        return clientTokenHeader;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getClientId() {
        return clientId;
    }
}
