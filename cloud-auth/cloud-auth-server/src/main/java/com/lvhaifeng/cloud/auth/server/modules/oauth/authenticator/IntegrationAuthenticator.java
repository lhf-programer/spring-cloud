package com.lvhaifeng.cloud.auth.server.modules.oauth.authenticator;

import com.lvhaifeng.cloud.auth.server.modules.oauth.bean.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.oauth.entity.IntegrationAuthentication;

/**
 * @Description 集成认证
 * @Author haifeng.lv
 * @Date 2019/12/16 17:36
 */
public interface IntegrationAuthenticator {
    /**
     * @Description 处理集成认证
     * @Author haifeng.lv
     * @param: integrationAuthentication
     * @Date 2019/12/16 17:36
     * @return: com.lvhaifeng.cloud.auth.server.modules.oauth.bean.OauthUser
     */
    OauthUser authenticate(IntegrationAuthentication integrationAuthentication);

    /**
     * @Description 进行预处理
     * @Author haifeng.lv
     * @param: integrationAuthentication
     * @Date 2019/12/16 17:36
     */
    void prepare(IntegrationAuthentication integrationAuthentication);

    /**
     * @Description 判断是否支持集成认证类型
     * @Author haifeng.lv
     * @param: integrationAuthentication
     * @Date 2019/12/16 17:36
     * @return: boolean
     */
    boolean support(IntegrationAuthentication integrationAuthentication);

    /**
     * @Description 认证结束后执行
     * @Author haifeng.lv
     * @param: integrationAuthentication
     * @Date 2019/12/16 17:37
     */
    void complete(IntegrationAuthentication integrationAuthentication);
}
