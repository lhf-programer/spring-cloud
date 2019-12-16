package com.lvhaifeng.cloud.auth.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 用户授权配置
 * @Author haifeng.lv
 * @Date 2019/12/16 17:30
 */
@Data
public class UserAuthConfig {
    @Value("${auth.user.token-header}")
    private String tokenHeader;
    @Value("${auth.user.limit-expire}")
    private Integer tokenLimitExpire;
    private byte[] pubKeyByte;
}
