package com.lvhaifeng.cloud.auth.client.jwt;

import com.lvhaifeng.cloud.auth.client.config.ServiceAuthConfig;
import com.lvhaifeng.cloud.auth.client.feign.ServiceAuthFeign;
import com.lvhaifeng.cloud.common.exception.auth.ClientTokenException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.jwt.JWTHelper;
import com.lvhaifeng.cloud.common.msg.BaseResponse;
import com.lvhaifeng.cloud.common.msg.ObjectRestResponse;
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
                throw new ClientTokenException("Client token expired!");
            }
            return infoFromToken;
        } catch (ExpiredJwtException ex) {
            throw new ClientTokenException("Client token expired!");
        } catch (SignatureException ex) {
            throw new ClientTokenException("Client token signature error!");
        } catch (IllegalArgumentException ex) {
            throw new ClientTokenException("User token is empty!");
        }
    }

    /**
     * 刷新可以访问的客户端
     *
     * @author haifeng.lv
     * @date 2019-07-30 17:58
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void refreshAllowedClient() {
        BaseResponse resp = serviceAuthFeign.getAllowedClient(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            ObjectRestResponse<List<String>> allowedClient = (ObjectRestResponse<List<String>>) resp;
            this.allowedClient = allowedClient.getData();
        }
    }

    /**
     * 刷新客户端token
     *
     * @author haifeng.lv
     * @date 2019-07-30 17:58
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void refreshClientToken() {
        log.debug("刷新客户端 token.....");
        BaseResponse resp = serviceAuthFeign.getAccessToken(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            ObjectRestResponse<String> clientToken = (ObjectRestResponse<String>) resp;
            this.clientToken = clientToken.getData();
        }
    }

    public String getClientToken() {
        if (this.clientToken == null) {
            this.refreshClientToken();
        }
        return clientToken;
    }

    /**
     * 获取允许访问的客户端
     *
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
