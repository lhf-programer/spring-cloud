package com.lvhaifeng.cloud.auth.client.service;

import com.lvhaifeng.cloud.auth.client.config.AuthClientConfig;
import com.lvhaifeng.cloud.auth.client.feign.IAuthClientFeign;
import com.lvhaifeng.cloud.common.exception.auth.ClientInvalidException;
import com.lvhaifeng.cloud.common.exception.auth.ClientTokenException;
import com.lvhaifeng.cloud.common.auth.AuthHelper;
import com.lvhaifeng.cloud.common.vo.AuthInfo;
import com.lvhaifeng.cloud.common.vo.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 客户端授权服务
 * @Author haifeng.lv
 * @Date 2019/12/16 17:32
 */
@Configuration
@Slf4j
@EnableScheduling
public class AuthClientService {
    @Autowired
    private AuthClientConfig authClientConfig;
    @Autowired
    private IAuthClientFeign authClientFeign;
    /**
     * 客户端 token
     */
    private String clientToken;

    /**
     * @Description 获取客户端信息
     * @Author haifeng.lv
     * @Date 2019/12/20 11:47
     */
    public AuthInfo getInfoFromToken(String token, String clientId) throws InvalidKeySpecException, NoSuchAlgorithmException {
        try {
            AuthInfo authInfo = AuthHelper.getInfoFromToken(token, authClientConfig.getPubKeyByte());
            LocalDateTime expireTime = authInfo.getExpireTime();
            if (Duration.between(expireTime, LocalDateTime.now()).toMillis() > 0) {
                throw new ClientTokenException("客户端 token过期！");
            }
            // 获取 jwt基本信息
            String id = authInfo.getId();
            // 获取所有允许的 client
            List<String> allowedClient = authClientFeign.getAllowedClient(clientId).getResult();
            if (!Collections.isEmpty(allowedClient)) {
                for (String client : allowedClient) {
                    if (client.equals(id)) {
                        return authInfo;
                    }
                }
            }

            throw new ClientInvalidException("该客户端不被允许");
        } catch (ExpiredJwtException ex) {
            throw new ClientTokenException("客户端 token过期！");
        } catch (SignatureException ex) {
            throw new ClientTokenException("客户端 token签名错误！");
        }
    }

    /**
     * @Description 设置当前客户端信息
     * @Author haifeng.lv
     * @param: request
     * @Date 2020/4/28 11:03
     */
    public boolean setCurrentClientInfo(HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        // 获取头部 token
        String token = request.getHeader(authClientConfig.getTokenHeader());
        if (StringUtils.isBlank(token)) {
            return false;
        }

        getInfoFromToken(token, authClientConfig.getClientId());
        return true;
    }

    /**
     * 刷新客户端token
     *
     * @author haifeng.lv
     * @date 2019-07-30 17:58
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void refreshClientToken() {
        log.info("刷新客户端 token.....");
        Result<String> accessToken = authClientFeign.getToken(authClientConfig.getClientId(), authClientConfig.getClientSecret());
        if (accessToken.isSuccess()) {
            this.clientToken = accessToken.getResult();
        }
    }

    /**
     * @Description 获取客户端 token
     * @Author haifeng.lv
     * @Date 2019/12/21 17:40
     * @return: java.lang.String
     */
    public String getClientToken() {
        if (this.clientToken == null) {
            this.refreshClientToken();
        }
        return clientToken;
    }

}
