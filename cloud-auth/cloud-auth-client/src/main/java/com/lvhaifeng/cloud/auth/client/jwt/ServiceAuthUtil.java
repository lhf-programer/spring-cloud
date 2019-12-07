package com.lvhaifeng.cloud.auth.client.jwt;

import com.lvhaifeng.cloud.auth.client.config.ServiceAuthConfig;
import com.lvhaifeng.cloud.auth.client.feign.ServiceAuthFeign;
import com.lvhaifeng.cloud.common.exception.auth.ClientTokenException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.jwt.JWTHelper;
import com.lvhaifeng.cloud.common.vo.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Configuration
@Slf4j
@EnableScheduling
public class ServiceAuthUtil {
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;
    @Autowired
    private ServiceAuthFeign serviceAuthFeign;
    @Autowired
    private JWTHelper jwtHelper;

    private List<String> allowedClient;
    private String clientToken;

    /**
     * 根据token获取用户信息
     *
     * @author haifeng.lv
     * @date 2019-07-30 17:57
     */
    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            IJWTInfo infoFromToken = jwtHelper.getInfoFromToken(token, serviceAuthConfig.getPubKeyByte());
            Date current = infoFromToken.getExpireTime();
            if (new Date().after(current)) {
                throw new ClientTokenException("客户端 token过期！");
            }
            return infoFromToken;
        } catch (ExpiredJwtException ex) {
            throw new ClientTokenException("客户端 token过期！");
        } catch (SignatureException ex) {
            throw new ClientTokenException("客户端 token签名错误！");
        } catch (IllegalArgumentException ex) {
            throw new ClientTokenException("客户端 token为空!");
        }
    }

    /**
     * 刷新可以访问的客户端
     * @author haifeng.lv
     * @date 2019-07-30 17:58
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void refreshAllowedClient() {
        Result<List<String>> resp = serviceAuthFeign.getAllowedClient(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.isSuccess()) {
            this.allowedClient = resp.getResult();
        }
    }

    /**
     * 刷新客户端token
     * @author haifeng.lv
     * @date 2019-07-30 17:58
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void refreshClientToken() {
        log.debug("刷新客户端 token.....");
        Result<String> accessToken = serviceAuthFeign.getAccessToken(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (accessToken.isSuccess()) {
            this.clientToken = accessToken.getResult();
        }
    }

    /**
     * @description 获取客户端 token
     * @author haifeng.lv
     * @updateTime 2019/12/3 14:09
     * @return: java.lang.String
     */
    public String getClientToken() {
        if (this.clientToken == null) {
            this.refreshClientToken();
        }
        return clientToken;
    }

    /**
     * 获取允许访问的客户端
     * @author haifeng.lv
     * @date 2019-07-30 14:03
     */
    public List<String> getAllowedClient() {
        if (this.allowedClient == null || CollectionUtils.isEmpty(this.allowedClient)) {
            this.refreshAllowedClient();
        }
        return allowedClient;
    }

}
