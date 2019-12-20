package com.lvhaifeng.cloud.auth.server.modules.user.service;

import com.lvhaifeng.cloud.auth.server.modules.user.authenticator.IntegrationAuthenticator;
import com.lvhaifeng.cloud.auth.server.modules.user.vo.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.user.entity.IntegrationAuthentication;
import com.lvhaifeng.cloud.auth.server.modules.user.entity.IntegrationAuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 集成认证用户服务
 * @Author haifeng.lv
 * @Date 2019/12/16 17:38
 */
@Service
public class IntegrationUserDetailsService implements UserDetailsService {
    private List<IntegrationAuthenticator> authenticators;

    @Autowired(required = false)
    public void setIntegrationAuthenticators(List<IntegrationAuthenticator> authenticators) {
        this.authenticators = authenticators;
    }

    @Override
    public OauthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        IntegrationAuthentication integrationAuthentication = IntegrationAuthenticationContext.get();
        // 判断是否是集成登录
        if (integrationAuthentication == null) {
            integrationAuthentication = new IntegrationAuthentication();
        }
        integrationAuthentication.setUsername(username);
        OauthUser oauthUser = this.authenticate(integrationAuthentication);

        return oauthUser;
    }

    /**
     * @Description 验证用户
     * @Author haifeng.lv
     * @param: integrationAuthentication
     * @Date 2019/12/16 17:38
     * @return: com.lvhaifeng.cloud.auth.server.modules.oauth.bean.OauthUser
     */
    private OauthUser authenticate(IntegrationAuthentication integrationAuthentication) {
        if (this.authenticators != null) {
            for (IntegrationAuthenticator authenticator : authenticators) {
                if (authenticator.support(integrationAuthentication)) {
                    return authenticator.authenticate(integrationAuthentication);
                }
            }
        }
        return null;
    }
}
