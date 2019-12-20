package com.lvhaifeng.cloud.auth.server.modules.user.authenticator;

import com.lvhaifeng.cloud.auth.server.modules.user.vo.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.user.entity.IntegrationAuthentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
    void prepare(IntegrationAuthentication integrationAuthentication, HttpServletRequest request, HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException;

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
