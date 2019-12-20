package com.lvhaifeng.cloud.auth.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description 用户授权配置
 * @Author haifeng.lv
 * @Date 2019/12/16 17:30
 */
@Data
public class AuthUserConfig {
    @Value("${auth.user.token-header}")
    private String tokenHeader;
    @Value("${auth.user.limit-expire}")
    private Integer tokenLimitExpire;
    private byte[] pubKeyByte;
}
