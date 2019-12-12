package com.lvhaifeng.cloud.auth.server.configuration;

import com.lvhaifeng.cloud.auth.server.interceptor.ServiceAuthInterceptor;
import com.lvhaifeng.cloud.auth.server.interceptor.UserAuthInterceptor;
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

import java.util.ArrayList;
import java.util.Collections;

/**
 * @description 服务全局配置
 * @author haifeng.lv
 * @updateTime 2019/12/12 17:04
 */
@Configuration
@Primary
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * @description 异常
     * @author haifeng.lv
     * @updateTime 2019/12/12 17:04
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

    /**
     * 忽略拦截
     */
    @Value("${resquest.skip}")
    private String matchers;

    private ArrayList<String> getExcludeCommonPathPatterns() {
        ArrayList<String> list = new ArrayList<>();
        String[] urls = matchers.split(",");
        Collections.addAll(list, urls);
        return list;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ArrayList<String> commonPathPatterns = getExcludeCommonPathPatterns();
        registry.addInterceptor(getServiceAuthRestInterceptor()).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
        registry.addInterceptor(getUserAuthRestInterceptor()).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
    }

    @Bean
    ServiceAuthInterceptor getServiceAuthRestInterceptor() {
        return new ServiceAuthInterceptor();
    }

    @Bean
    UserAuthInterceptor getUserAuthRestInterceptor() {
        return new UserAuthInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
