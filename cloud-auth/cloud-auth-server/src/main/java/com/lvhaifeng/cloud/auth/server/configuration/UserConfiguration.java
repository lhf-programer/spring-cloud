package com.lvhaifeng.cloud.auth.server.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 用户配置
 * @Author haifeng.lv
 * @Date 2019/12/16 17:33
 */
@Configuration
@Data
public class UserConfiguration {
    @Value("${jwt.token-header}")
    private String userTokenHeader;
}
