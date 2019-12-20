package com.lvhaifeng.cloud.auth.client.jwt;

import com.lvhaifeng.cloud.auth.client.config.AuthClientConfig;
import com.lvhaifeng.cloud.auth.client.feign.IAuthClientFeign;
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

import java.util.Date;

/**
 * @Description 服务授权工具类
 * @Author haifeng.lv
 * @Date 2019/12/16 17:32
 */
@Configuration
@Slf4j
@EnableScheduling
public class AuthClientUtil {
    @Autowired
    private AuthClientConfig serviceAuthConfig;
    @Autowired
    private IAuthClientFeign authClientFeign;

    /**
     * 客户端 token
     */
    private String clientToken;

    /**
     * 根据token获取客户端信息
     * @author haifeng.lv
     * @date 2019-07-30 17:57
     */
    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            IJWTInfo infoFromToken = JWTHelper.getInfoFromToken(token, serviceAuthConfig.getPubKeyByte());
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
     * 刷新客户端token
     * @author haifeng.lv
     * @date 2019-07-30 17:58
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void refreshClientToken() {
        log.debug("刷新客户端 token.....");
        Result<String> accessToken = authClientFeign.getAccessToken(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
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

}
