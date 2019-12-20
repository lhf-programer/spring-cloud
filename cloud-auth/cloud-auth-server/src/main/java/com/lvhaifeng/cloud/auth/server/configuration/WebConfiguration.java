package com.lvhaifeng.cloud.auth.server.configuration;

import com.lvhaifeng.cloud.auth.server.interceptor.AuthClientInterceptor;
import com.lvhaifeng.cloud.common.exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description 服务全局配置
 * @Author haifeng.lv
 * @Date 2019/12/16 17:33
 */
@Configuration
@Primary
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * @Description 异常
     * @Author haifeng.lv
     * @Date 2019/12/16 17:34
     * @return: com.lvhaifeng.cloud.common.exception.GlobalExceptionHandler
     */
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Value("${resquest.skip}")
    private String matchers;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] urls = matchers.split(",");
        registry.addInterceptor(getAuthClientInterceptor()).addPathPatterns("/**").excludePathPatterns(urls);
    }

    @Bean
    AuthClientInterceptor getAuthClientInterceptor() {
        return new AuthClientInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
