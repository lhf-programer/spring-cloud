package com.lvhaifeng.cloud.gate.config;

import com.lvhaifeng.cloud.auth.client.interceptor.ServiceFeignInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @Description feign 配置拦截器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:53
 */
public class FeignConfiguration {
    @Bean
    ServiceFeignInterceptor getClientTokenInterceptor(){
        return new ServiceFeignInterceptor();
    }
}
