package com.lvhaifeng.cloud.admin.config;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lvhaifeng.cloud.auth.client.interceptor.ServiceAuthRestInterceptor;
import com.lvhaifeng.cloud.auth.client.interceptor.UserAuthRestInterceptor;
import com.lvhaifeng.cloud.common.exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description web 总配置类
 * @Author haifeng.lv
 * @Date 2019/12/16 17:55
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * @Description 异常总拦截器
     * @Author haifeng.lv
     * @Date 2019/12/16 17:56
     * @return: com.lvhaifeng.cloud.common.exception.GlobalExceptionHandler
     */
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ArrayList<String> commonPathPatterns = getExcludeCommonPathPatterns();
        registry.addInterceptor(getServiceAuthRestInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(commonPathPatterns.toArray(new String[]{}))
                .excludePathPatterns("/userApp/*");
        commonPathPatterns.add("/user/validate");
        registry.addInterceptor(getUserAuthRestInterceptor()).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
    }

    @Bean
    ServiceAuthRestInterceptor getServiceAuthRestInterceptor() {
        return new ServiceAuthRestInterceptor();
    }

    @Bean
    UserAuthRestInterceptor getUserAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
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
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();
        messageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        messageConverter.getFastJsonConfig().setFeatures(Feature.AutoCloseSource);
        converters.add(messageConverter);
    }
}
