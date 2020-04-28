package com.lvhaifeng.cloud.auth.user.service;

import com.lvhaifeng.cloud.auth.user.config.AuthUserConfig;
import com.lvhaifeng.cloud.common.constant.RequestHeaderConstants;
import com.lvhaifeng.cloud.common.context.BaseContextHandler;
import com.lvhaifeng.cloud.common.exception.auth.NonLoginException;
import com.lvhaifeng.cloud.common.auth.AuthHelper;
import com.lvhaifeng.cloud.common.vo.AuthInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
        }
    }

    /**
     * @Description 设置当前用户信息
     * @Author haifeng.lv
     * @param: request
     * @Date 2020/4/28 10:45
     * @return: boolean 是否设置成功
     */
    public boolean setCurrentUserInfo(HttpServletRequest request) throws Exception {
        // 获取用户 token
        String token = request.getHeader(userAuthConfig.getTokenHeader());
        // 如果为空则到 cookie中查找
        if (StringUtils.isEmpty(token)) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(userAuthConfig.getTokenHeader())) {
                        token = cookie.getValue();
                    }
                }
            }
        }

        // 截取 token
        if (token != null && token.startsWith(RequestHeaderConstants.JWT_TOKEN_TYPE)) {
            token = token.substring(RequestHeaderConstants.JWT_TOKEN_TYPE.length());
        } else {
            return false;
        }

        // 解析 token
        AuthInfo authInfo = this.getInfoFromToken(token);
        BaseContextHandler.setToken(token);
        BaseContextHandler.setUserName(authInfo.getName());
        BaseContextHandler.setUserId(authInfo.getId());
        return true;
    }
}
