package com.lvhaifeng.cloud.gate.filter;

import com.lvhaifeng.cloud.api.vo.authority.PermissionInfo;
import com.lvhaifeng.cloud.api.vo.log.LogInfo;
import com.lvhaifeng.cloud.auth.client.config.ServiceAuthConfig;
import com.lvhaifeng.cloud.auth.client.config.UserAuthConfig;
import com.lvhaifeng.cloud.auth.client.jwt.ServiceAuthUtil;
import com.lvhaifeng.cloud.auth.client.jwt.UserAuthUtil;
import com.lvhaifeng.cloud.common.constant.RequestHeaderConstants;
import com.lvhaifeng.cloud.common.context.BaseContextHandler;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.util.RequestUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @Description 路由过滤器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:53
 */
@Component
@Slf4j
@RefreshScope
public class AdminAccessFilter extends ZuulFilter {
    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Value("${zuul.prefix}")
    private String zuulPrefix;

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

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
        final String requestUri = request.getRequestURI().substring(zuulPrefix.length());
        final String method = request.getMethod();
        BaseContextHandler.setToken(null);
        // 不进行拦截的地址
        if (isStartWith(requestUri) || HttpMethod.OPTIONS.matches(method)) {
            return null;
        }

        // 申请客户端密钥头
        ctx.addZuulRequestHeader(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        BaseContextHandler.remove();
        return null;
    }

    /**
     * @Description 获取目标权限资源
     * @Author haifeng.lv
     * @param: requestUri 请求地址
     * @param: method 方法
     * @param: serviceInfo 服务信息
     * @Date 2019/12/16 17:53
     * @return: java.util.stream.Stream<com.lvhaifeng.cloud.api.vo.authority.PermissionInfo>
     */
    private Stream<PermissionInfo> getPermissionIfs(final String requestUri, final String method, List<PermissionInfo> serviceInfo) {
        return serviceInfo.parallelStream().filter(new Predicate<PermissionInfo>() {
            @Override
            public boolean test(PermissionInfo permissionInfo) {
                String uri = permissionInfo.getUri();
                if (uri.indexOf("{") > 0) {
                    uri = uri.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                }
                String regEx = "^" + uri + "$";
                return (Pattern.compile(regEx).matcher(requestUri).find())
                        && method.equals(permissionInfo.getMethod());
            }
        });
    }

    private void setCurrentUserInfoAndLog(RequestContext ctx, IJWTInfo user, PermissionInfo pm) {
        String host = RequestUtil.getRequestIp();
        LogInfo logInfo = new LogInfo(pm.getMenu(), pm.getName(), pm.getUri(), LocalDateTime.now(), user.getId(), user.getName(), host);
    }

    /**
     * @Description 返回session中的用户信息
     * @Author haifeng.lv
     * @param: request
     * @param: ctx
     * @Date 2019/12/16 17:54
     * @return: com.lvhaifeng.cloud.common.jwt.IJWTInfo
     */
    private IJWTInfo getJWTUser(HttpServletRequest request, RequestContext ctx) throws Exception {
        String authToken = request.getHeader(userAuthConfig.getTokenHeader());
        String originToken = null;
        if (StringUtils.isBlank(authToken)) {
            authToken = request.getParameter("token");
        }
        if (authToken != null && authToken.startsWith(RequestHeaderConstants.JWT_TOKEN_TYPE)) {
            originToken = authToken;
            authToken = authToken.substring(RequestHeaderConstants.JWT_TOKEN_TYPE.length(), authToken.length());
        }
        ctx.addZuulRequestHeader(userAuthConfig.getTokenHeader(), originToken);
        BaseContextHandler.setToken(originToken);
        return userAuthUtil.getInfoFromToken(authToken);
    }

    /**
     * @Description URI是否以什么打头
     * @Author haifeng.lv
     * @param: requestUri 请求地址
     * @Date 2019/12/16 17:54
     * @return: boolean
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            // 去除空格
            s = s.replaceAll(" ","");
            int index;
            if ((index = s.indexOf("*")) != -1) {
                s = s.substring(0, index);
            }

            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * @Description 网关抛异常
     * @Author haifeng.lv
     * @param: body
     * @param: code
     * @Date 2019/12/16 17:54
     */
    private void setFailedRequest(String body, int code) {
        log.debug("Reporting error ({}): {}", code, body);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
        }
    }

}
