package com.lvhaifeng.cloud.auth.user.configuration;

import com.lvhaifeng.cloud.auth.user.config.AuthUserConfig;
import com.lvhaifeng.cloud.common.hystrix.BaseHystrixConcurrencyStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 授权配置
 * @Author haifeng.lv
 * @Date 2019/12/16 17:30
 */
@Configuration
@ComponentScan({"com.lvhaifeng.cloud.auth.user"})
public class AutoUserConfiguration {
    @Bean
    AuthUserConfig getUserAuthConfig() {
        return new AuthUserConfig();
    }

    @Bean
    public BaseHystrixConcurrencyStrategy newBaseHystrixConcurrencyStrategy() {
        return new BaseHystrixConcurrencyStrategy();
    }
}
