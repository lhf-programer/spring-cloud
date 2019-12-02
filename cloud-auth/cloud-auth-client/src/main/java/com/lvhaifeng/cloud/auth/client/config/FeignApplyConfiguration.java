
package com.lvhaifeng.cloud.auth.client.config;

import com.lvhaifeng.cloud.auth.client.interceptor.ServiceFeignInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * feign配置注入
 *
 * @author haifeng.lv
 * @date 2019-07-27 15:05
 */
public class FeignApplyConfiguration {
    @Bean
    ServiceFeignInterceptor getClientTokenInterceptor() {
        return new ServiceFeignInterceptor();
    }
}
