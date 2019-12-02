

package com.lvhaifeng.cloud.auth.client.interceptor;

import com.lvhaifeng.cloud.auth.client.config.ServiceAuthConfig;
import com.lvhaifeng.cloud.auth.client.config.UserAuthConfig;
import com.lvhaifeng.cloud.auth.client.jwt.ServiceAuthUtil;
import com.lvhaifeng.cloud.common.constant.RestCodeConstants;
import com.lvhaifeng.cloud.common.context.BaseContextHandler;
import lombok.extern.java.Log;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author haifeng.lv
 */
@Component
@Log
public class OkHttpTokenInterceptor implements Interceptor {
    @Autowired
    @Lazy
    private ServiceAuthUtil serviceAuthUtil;
    @Autowired
    @Lazy
    private ServiceAuthConfig serviceAuthConfig;
    @Autowired
    @Lazy
    private UserAuthConfig userAuthConfig;

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取本次请求信息
        Request newRequest = chain.request()
                .newBuilder()
                .header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken())
                .build();
        // 执行下一个拦截器 然后获得返回
        Response response = chain.proceed(newRequest);

        if (HttpStatus.FORBIDDEN.value() == response.code()) {
            if (response.body().string().contains(String.valueOf(RestCodeConstants.EX_CLIENT_INVALID_CODE))) {
                log.info("客户端令牌过期，请重试...");
                // 如果过期则刷新 token
                serviceAuthUtil.refreshClientToken();
                // 刷新 token
                newRequest = chain.request()
                        .newBuilder()
                        .header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken())
                        .header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken())
                        .build();
                response = chain.proceed(newRequest);
            }
        }
        return response;
    }

}
