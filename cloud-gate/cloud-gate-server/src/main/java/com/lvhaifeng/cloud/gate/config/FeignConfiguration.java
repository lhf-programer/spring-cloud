package com.lvhaifeng.cloud.gate.config;

import com.lvhaifeng.cloud.auth.client.interceptor.ServiceFeignInterceptor;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author haifeng.lv
 * @date 2018/9/12
 */
public class FeignConfiguration {
    @Bean
    ServiceFeignInterceptor getClientTokenInterceptor(){
        return new ServiceFeignInterceptor();
    }
}
