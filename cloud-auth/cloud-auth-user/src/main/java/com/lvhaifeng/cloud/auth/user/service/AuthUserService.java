package com.lvhaifeng.cloud.auth.user.service;

import com.lvhaifeng.cloud.auth.user.config.AuthUserConfig;
import com.lvhaifeng.cloud.common.exception.auth.NonLoginException;
import com.lvhaifeng.cloud.common.auth.AuthHelper;
import com.lvhaifeng.cloud.common.vo.AuthInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @Description 用户授权工具类
 * @Author haifeng.lv
 * @Date 2019/12/16 17:32
 */
@Configuration
public class AuthUserService {
    @Autowired
    private AuthUserConfig userAuthConfig;

    public AuthInfo getInfoFromToken(String token) throws Exception {
        try {
            AuthInfo authInfo = AuthHelper.getInfoFromToken(token, userAuthConfig.getPubKeyByte());
            // jwt 的默认超时时间 + 用户的超时时间 是否在现在之前
            if (Duration.between(authInfo.getExpireTime(), LocalDateTime.now()).toMillis() > 0) {
                throw new NonLoginException("用户 token过期！");
            }
            return authInfo;
        } catch (ExpiredJwtException ex) {
            throw new NonLoginException("用户 token过期！");
        } catch (SignatureException ex) {
            throw new NonLoginException("用户签名错误！");
        } catch (IllegalArgumentException ex) {
            throw new NonLoginException("用户 token为空！");
        }
    }
}
