package com.lvhaifeng.cloud.auth.server.configuration;

import com.lvhaifeng.cloud.auth.server.interceptor.ClientTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description Feign 配置器
 * @Author haifeng.lv
 * @Date 2019/12/21 14:42
 */
@Configuration
public class FeignConfiguration {
    @Bean
    ClientTokenInterceptor getClientTokenInterceptor(){
        return new ClientTokenInterceptor();
    }
}
