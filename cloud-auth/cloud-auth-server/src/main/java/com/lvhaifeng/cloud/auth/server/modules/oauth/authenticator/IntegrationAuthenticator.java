package com.lvhaifeng.cloud.auth.server.modules.oauth.authenticator;


import com.lvhaifeng.cloud.auth.server.modules.oauth.bean.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.oauth.entity.IntegrationAuthentication;

/**
 * 集成认证
 *
 * @author loki
 * @date 2019-07-29 13:37
 */
public interface IntegrationAuthenticator {

    /**
     * 处理集成认证
     *
     * @param integrationAuthentication
     * @return
     */
    OauthUser authenticate(IntegrationAuthentication integrationAuthentication);


    /**
     * 进行预处理
     *
     * @param integrationAuthentication
     */
    void prepare(IntegrationAuthentication integrationAuthentication);

    /**
     * 判断是否支持集成认证类型
     *
     * @param integrationAuthentication
     * @return
     */
    boolean support(IntegrationAuthentication integrationAuthentication);

    /**
     * 认证结束后执行
     *
     * @param integrationAuthentication
     */
    void complete(IntegrationAuthentication integrationAuthentication);

}
