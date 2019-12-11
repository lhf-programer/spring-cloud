
package com.lvhaifeng.cloud.auth.client.config;

import com.lvhaifeng.cloud.auth.client.interceptor.OkHttpTokenInterceptor;
import feign.Feign;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@AutoConfigureBefore(FeignAutoConfiguration.class)
@Configuration
@ConditionalOnClass(Feign.class)
public class FeignOkHttpConfig {

    @Autowired
    OkHttpTokenInterceptor okHttpLoggingInterceptor;

    private int feignOkHttpReadTimeout = 60;
    private int feignConnectTimeout = 60;
    private int feignWriteTimeout = 120;

    /**
     * @description http 基本配置
     * @author haifeng.lv
     * @updateTime 2019/11/30 14:23
     * @return: okhttp3.OkHttpClient
     */
    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new okhttp3.OkHttpClient.Builder().readTimeout(feignOkHttpReadTimeout, TimeUnit.SECONDS).connectTimeout(feignConnectTimeout, TimeUnit.SECONDS)
                .writeTimeout(feignWriteTimeout, TimeUnit.SECONDS).connectionPool(new ConnectionPool())
                .addInterceptor(okHttpLoggingInterceptor)
                .build();
    }
}