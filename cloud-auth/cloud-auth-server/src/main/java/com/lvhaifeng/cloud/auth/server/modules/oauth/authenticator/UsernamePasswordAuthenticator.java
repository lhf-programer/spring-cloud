package com.lvhaifeng.cloud.auth.server.modules.oauth.authenticator;

import com.lvhaifeng.cloud.auth.server.feign.IUserFeign;
import com.lvhaifeng.cloud.auth.server.modules.oauth.bean.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.oauth.entity.IntegrationAuthentication;
import com.lvhaifeng.cloud.common.vo.Result;
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
 * @author haifeng.lv
 * @date 2019-07-29 10:35
 */
@Component
@Primary
public class UsernamePasswordAuthenticator extends AbstractPreparableIntegrationAuthenticator {

    @Autowired
    private IUserFeign iUserFeign;

    @Override
    public OauthUser authenticate(IntegrationAuthentication integrationAuthentication) {
        String username = integrationAuthentication.getUsername();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }

        //获取用户基本信息
        Result<Map<String, String>> response = iUserFeign.getUserInfoByUsername(username);
        Map<String, String> data = response.getResult();

        //获取用户关联主机
        return new OauthUser(data.get("id"), data.get("username"), data.get("password"), authorities);
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.isEmpty(integrationAuthentication.getAuthType());
    }
}
