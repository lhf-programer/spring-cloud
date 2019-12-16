
package com.lvhaifeng.cloud.auth.client.config;

import com.lvhaifeng.cloud.auth.client.interceptor.ServiceFeignInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @Description feign配置注入
 * @Author haifeng.lv
 * @Date 2019/12/16 17:29
 */
public class FeignApplyConfiguration {
    @Bean
    ServiceFeignInterceptor getClientTokenInterceptor() {
        return new ServiceFeignInterceptor();
    }
}
