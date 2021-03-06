package com.lvhaifeng.cloud.gate.ratelimit.filters;

import com.google.common.net.HttpHeaders;
import com.lvhaifeng.cloud.gate.ratelimit.config.Rate;
import com.lvhaifeng.cloud.gate.ratelimit.config.repository.limiter.RateLimiter;
import com.lvhaifeng.cloud.gate.ratelimit.config.properties.RateLimitProperties;
import com.lvhaifeng.cloud.gate.ratelimit.config.properties.RateLimitProperties.Policy;
import com.lvhaifeng.cloud.gate.ratelimit.config.properties.RateLimitProperties.Policy.Type;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * @Description 限流过滤器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:52
 */
@RequiredArgsConstructor
public class RateLimitFilter extends ZuulFilter {
    public static final String LIMIT_HEADER = "X-RateLimit-Limit";
    public static final String REMAINING_HEADER = "X-RateLimit-Remaining";
    public static final String RESET_HEADER = "X-RateLimit-Reset";
    private static final String ANONYMOUS_USER = "anonymous";

    private static final UrlPathHelper URL_PATH_HELPER = new UrlPathHelper();

    private final RateLimiter rateLimiter;
    private final RateLimitProperties properties;
    private final RouteLocator routeLocator;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return properties.isEnabled() && policy().isPresent();
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletResponse response = ctx.getResponse();
        final HttpServletRequest request = ctx.getRequest();

        policy().ifPresent(policy -> {
            final Rate rate = rateLimiter.consume(policy, key(request, policy.getType()));
            response.setHeader(LIMIT_HEADER, policy.getLimit().toString());
            response.setHeader(REMAINING_HEADER, String.valueOf(Math.max(rate.getRemaining(), 0)));
            response.setHeader(RESET_HEADER, rate.getReset().toString());
            if (rate.getRemaining() < 0) {
                ctx.setResponseStatusCode(TOO_MANY_REQUESTS.value());
                ctx.put("rateLimitExceeded", "true");
                throw new ZuulRuntimeException(new ZuulException(TOO_MANY_REQUESTS.toString(),
                        TOO_MANY_REQUESTS.value(), null));
            }
        });
        return null;
    }

    /**
     * @Description 获取路由配置
     * @Author haifeng.lv
     * @Date 2019/12/18 14:41
     * @return: org.springframework.cloud.netflix.zuul.filters.Route
     */
    private Route route() {
        // 获取请求地址
        String requestURI = URL_PATH_HELPER.getPathWithinApplication(RequestContext.getCurrentContext().getRequest());
        return routeLocator.getMatchingRoute(requestURI);
    }

    private Optional<Policy> policy() {
        Route route = route();
        if (route != null) {
            return properties.getPolicy(route.getId());
        }
        return Optional.ofNullable(properties.getDefaultPolicy());
    }

    /**
     * @Description 组装 key
     * @Author haifeng.lv
     * @param: request
     * @param: types
     * @Date 2019/12/18 14:36
     * @return: java.lang.String
     */
    private String key(final HttpServletRequest request, final List<Type> types) {
        final Route route = route();
        if (null == route) {
            // 没有找到路由
            throw new ZuulRuntimeException(new ZuulException(NO_CONTENT.toString(),
                    NO_CONTENT.value(), null));
        }

        final StringJoiner joiner = new StringJoiner(":");
        joiner.add(properties.getKeyPrefix());
        joiner.add(route.getId());
        if (null != types && !types.isEmpty()) {
            if (types.contains(Type.URL)) {
                // 路径
                joiner.add(route.getPath());
            }
            if (types.contains(Type.ORIGIN)) {
                // 地址
                joiner.add(getRemoteAddr(request));
            }
        }
        joiner.add(ANONYMOUS_USER);
        return joiner.toString();
    }

    /**
     * @Description 获取地址
     * @Author haifeng.lv
     * @param: request
     * @Date 2019/12/18 14:39
     * @return: java.lang.String
     */
    private String getRemoteAddr(final HttpServletRequest request) {
        if (properties.isBehindProxy() && request.getHeader(HttpHeaders.X_FORWARDED_FOR) != null) {
            return request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        }
        return request.getRemoteAddr();
    }
}
