package com.lvhaifeng.cloud.auth.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description 服务授权配置
 * @Author haifeng.lv
 * @Date 2019/12/16 17:30
 */
@Getter
@Setter
public class AuthClientConfig {
    private byte[] pubKeyByte;
    @Value("${auth.client.id}")
    private String clientId;
    @Value("${auth.client.secret}")
    private String clientSecret;
    @Value("${auth.client.token-header}")
    private String tokenHeader;
}
