package com.lvhaifeng.cloud.auth.client.interceptor;

import com.lvhaifeng.cloud.auth.client.config.AuthClientConfig;
import com.lvhaifeng.cloud.auth.client.jwt.AuthClientUtil;
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
 * @Description okHttp 拦截器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:31
 */
@Component
@Log
public class OkHttpTokenInterceptor implements Interceptor {
    @Autowired
    @Lazy
    private AuthClientUtil serviceAuthUtil;
    @Autowired
    @Lazy
    private AuthClientConfig serviceAuthConfig;

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取本次请求信息
        Request newRequest = chain.request()
                .newBuilder()
                .build();
        // 执行下一个拦截器 然后获得返回
        Response response = chain.proceed(newRequest);
        if (HttpStatus.FORBIDDEN.value() == response.code()) {
            if (response.body().string().contains(String.valueOf(RestCodeConstants.EX_CLIENT_INVALID_CODE))) {
                log.info("客户端 token已过期请重试");
                serviceAuthUtil.refreshClientToken();
                newRequest = chain.request()
                        .newBuilder()
                        .header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken())
                        .build();
                response = chain.proceed(newRequest);
            }
        }
        return response;
    }

}
