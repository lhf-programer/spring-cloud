package com.lvhaifeng.cloud.gate.filter;

import com.lvhaifeng.cloud.auth.client.config.AuthClientConfig;
import com.lvhaifeng.cloud.auth.client.service.AuthClientService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 路由过滤器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:53
 */
@Component
@Slf4j
@RefreshScope
public class AdminAccessFilter extends ZuulFilter {
    @Value("${zuul.prefix}")
    private String zuulPrefix;
    @Autowired
    private AuthClientConfig authClientConfig;
    @Autowired
    private AuthClientService authClientService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String method = request.getMethod();
        // 不进行拦截的请求
        if (!HttpMethod.OPTIONS.matches(method)) {
            // 申请客户端密钥头
            ctx.addZuulRequestHeader(authClientConfig.getTokenHeader(), authClientService.getClientToken());
        }

        return null;
    }

}
