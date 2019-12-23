package com.lvhaifeng.cloud.auth.server.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 客户端与用户端密钥
 * @Author haifeng.lv
 * @Date 2019/12/20 16:16
 */
@Configuration
@Data
public class KeyConfiguration {
    @Value("${user.rsa-secret}")
    private String userSecret;
    @Value("${client.rsa-secret}")
    private String clientSecret;
    private byte[] userPubKey;
    private byte[] userPriKey;
    private byte[] clientPriKey;
    private byte[] clientPubKey;
}
