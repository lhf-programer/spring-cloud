package com.lvhaifeng.cloud.auth.server.modules.user.authenticator.impl;

import com.lvhaifeng.cloud.auth.server.configuration.ClientConfiguration;
import com.lvhaifeng.cloud.auth.server.feign.IUserFeign;
import com.lvhaifeng.cloud.auth.server.jwt.client.ClientTokenHelper;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import com.lvhaifeng.cloud.auth.server.modules.user.authenticator.AbstractPrepareIntegrationAuthenticator;
import com.lvhaifeng.cloud.auth.server.modules.user.vo.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.user.entity.IntegrationAuthentication;
import com.lvhaifeng.cloud.common.exception.auth.ClientInvalidException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description 登入处理
 * @Author haifeng.lv
 * @Date 2019/12/16 17:37
 */
@Component
@Primary
public class UsernamePasswordAuthenticator extends AbstractPrepareIntegrationAuthenticator {
    @Autowired
    private ClientTokenHelper clientTokenHelper;
    @Autowired
    private ClientConfiguration clientConfiguration;

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

        // 获取用户基本信息
        Result<Map<String, String>> response = iUserFeign.getUserInfoByUsername(username);
        Map<String, String> data = response.getResult();
        if (data == null) {
            throw new UsernameNotFoundException("该用户未找到");
        }

        // 获取用户关联主机
        return new OauthUser(data.get("id"), data.get("username"), data.get("password"), authorities);
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication, HttpServletRequest request, HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String token = request.getHeader(clientConfiguration.getClientTokenHeader());
        if (StringUtils.isNotBlank(token) && clientTokenHelper.verifyClientToken(token, clientConfiguration.getClientId())) {
            return;
        }
        throw new ClientInvalidException("该客户端不被允许!");
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.isEmpty(integrationAuthentication.getAuthType());
    }

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {
        super.complete(integrationAuthentication);
    }
}
