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
 * @Description 网关配置项
 * @Author haifeng.lv
 * @Date 2019/12/16 17:53
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
     * @Description xssFilter注册
     * @Author haifeng.lv
     * @Date 2019/12/16 17:53
     * @return: org.springframework.boot.web.servlet.FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }

    /**
     * @Description 使用redis 路由
     * @Author haifeng.lv
     * @Date 2019/12/18 14:16
     * @return: com.lvhaifeng.cloud.gate.route.RedisRouteLocator
     */
    @Bean
    RedisRouteLocator redisRouteLocator() {
        RedisRouteLocator redisRouteLocator = new RedisRouteLocator(null == this.server.getServlet().getContextPath() ? "/":this.server.getServlet().getContextPath(), this.zuulProperties);
        redisRouteLocator.setRedisTemplate(this.redisTemplate);
        return redisRouteLocator;
    }
}
