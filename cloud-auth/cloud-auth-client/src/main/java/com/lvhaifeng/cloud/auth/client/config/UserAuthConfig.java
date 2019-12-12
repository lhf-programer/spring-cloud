package com.lvhaifeng.cloud.auth.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * @description 用户授权配置
 * @author haifeng.lv
 * @updateTime 2019/11/30 14:24
 */
@Data
public class UserAuthConfig {
    @Value("${auth.user.token-header}")
    private String tokenHeader;
    @Value("${auth.user.limit-expire}")
    private Integer tokenLimitExpire;
    private byte[] pubKeyByte;
}
