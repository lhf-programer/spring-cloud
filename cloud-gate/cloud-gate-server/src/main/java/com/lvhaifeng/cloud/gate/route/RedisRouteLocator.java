package com.lvhaifeng.cloud.gate.route;

import com.alibaba.fastjson.JSON;
import com.lvhaifeng.cloud.common.constant.RedisKeyConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 路由服务
 * @Author haifeng.lv
 * @Date 2019/12/16 17:54
 */
@Slf4j
public class RedisRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {
    private RedisTemplate<String, String> redisTemplate;
    private ZuulProperties properties;

    public RedisRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        log.info("servletPath:{}", servletPath);
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromRedis());
        //优化一下配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // 如果没有出现，在前面加上斜杠
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    /**
     * @Description 从 redis加载路由
     * @Author haifeng.lv
     * @Date 2019/12/16 17:54
     * @return: java.util.Map<java.lang.String,org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute>
     */
    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromRedis() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
        List<ZuulRouteVO> results = JSON.parseArray(redisTemplate.opsForValue().get(RedisKeyConstants.ZUUL_ROUTE_KEY), ZuulRouteVO.class);
        for (ZuulRouteVO result : results) {
            if (!result.getEnabled()) {
                continue;
            }
            if (StringUtils.isEmpty(result.getPath())) {
                continue;
            }
            if (StringUtils.isEmpty(result.getServiceId()) && StringUtils.isEmpty(result.getUrl())) {
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(result, zuulRoute);
            } catch (Exception e) {
                log.error("=============加载路由错误==============", e);
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @Description 路由基础类
     * @Author haifeng.lv
     * @Date 2019/12/16 17:54
     */
    @Getter
    @Setter
    public static class ZuulRouteVO {

        private String id;

        private String path;

        private String serviceId;

        private String url;

        private boolean stripPrefix = true;

        private Boolean retryable;

        private Boolean enabled;
    }
}
