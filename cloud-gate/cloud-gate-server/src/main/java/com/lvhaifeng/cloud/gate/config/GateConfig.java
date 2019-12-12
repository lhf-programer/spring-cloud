package com.lvhaifeng.cloud.gate.config;

import com.lvhaifeng.cloud.common.xss.XssFilter;
import com.lvhaifeng.cloud.gate.route.RedisRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * 网关配置项
 * @author haifeng.lv
 * @create 2018/2/12
 */
@Configuration
public class GateConfig {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    ZuulProperties zuulProperties;
    @Autowired
    ServerProperties server;

    /**
     * @description xssFilter注册
     * @author haifeng.lv
     * @updateTime 2019/12/12 17:42
     * @return: org.springframework.boot.web.servlet.FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }

    @Bean
    RedisRouteLocator redisRouteLocator() {
        RedisRouteLocator redisRouteLocator = new RedisRouteLocator(null == this.server.getServlet().getContextPath() ? "/":this.server.getServlet().getContextPath(), this.zuulProperties);
        redisRouteLocator.setRedisTemplate(this.redisTemplate);
        return redisRouteLocator;
    }
}
