package com.lvhaifeng.cloud.auth.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * 服务授权配置
 *
 * @author haifeng.lv
 * @date 2019-07-29 14:00
 */
@Getter
@Setter
public class ServiceAuthConfig {
    private byte[] pubKeyByte;
    @Value("${auth.client.id:null}")
    private String clientId;
    @Value("${auth.client.secret}")
    private String clientSecret;
    @Value("${auth.client.token-header}")
    private String tokenHeader;
    @Value("${spring.application.name}")
    private String applicationName;
}
