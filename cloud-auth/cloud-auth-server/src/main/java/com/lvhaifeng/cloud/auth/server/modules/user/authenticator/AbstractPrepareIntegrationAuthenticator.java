package com.lvhaifeng.cloud.auth.server.modules.user.authenticator;

import com.lvhaifeng.cloud.auth.server.modules.user.vo.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.user.entity.IntegrationAuthentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public abstract class AbstractPrepareIntegrationAuthenticator implements IntegrationAuthenticator {
    @Override
    public abstract OauthUser authenticate(IntegrationAuthentication integrationAuthentication);

    @Override
    public abstract void prepare(IntegrationAuthentication integrationAuthentication, HttpServletRequest request, HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException;

    @Override
    public abstract boolean support(IntegrationAuthentication integrationAuthentication);

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {
    }
}
