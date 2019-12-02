package com.lvhaifeng.cloud.auth.server.modules.oauth.authenticator;

import com.lvhaifeng.cloud.auth.server.feign.IUserFeign;
import com.lvhaifeng.cloud.auth.server.modules.oauth.bean.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.oauth.biz.OauthClientDetailsBiz;
import com.lvhaifeng.cloud.auth.server.modules.oauth.entity.IntegrationAuthentication;
import com.lvhaifeng.cloud.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 登入处理
 *
 * @author loki
 * @date 2019-07-29 10:35
 */
@Component
@Primary
public class UsernamePasswordAuthenticator extends AbstractPreparableIntegrationAuthenticator {

    @Autowired
    private IUserFeign iUserFeign;

    @Autowired
    private OauthClientDetailsBiz oauthClientDetailsBiz;

    @Override
    public OauthUser authenticate(IntegrationAuthentication integrationAuthentication) {
        String username = integrationAuthentication.getUsername();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }

        //获取用户基本信息
        ObjectRestResponse<Map<String, String>> response = iUserFeign.getUserInfoByUsername(username);
        Map<String, String> data = response.getData();

        //获取用户关联主机
        return oauthClientDetailsBiz.getLoginUserFullInfo(data.get("id"), data.get("username"), data.get("password"), data.get("name"), authorities);
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.isEmpty(integrationAuthentication.getAuthType());
    }
}
