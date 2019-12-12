package com.lvhaifeng.cloud.auth.client.jwt;

import com.lvhaifeng.cloud.auth.client.config.UserAuthConfig;
import com.lvhaifeng.cloud.common.exception.auth.NonLoginException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.jwt.JWTHelper;
import com.lvhaifeng.cloud.common.util.RedisKeyUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description 用户授权工具类
 * @author haifeng.lv
 * @updateTime 2019/12/12 16:59
 */
@Configuration
public class UserAuthUtil {
    @Autowired
    private UserAuthConfig userAuthConfig;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private JWTHelper jwtHelper;

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            IJWTInfo infoFromToken = jwtHelper.getInfoFromToken(token, userAuthConfig.getPubKeyByte());
            if (redisTemplate.hasKey(RedisKeyUtil.buildUserDisableKey(infoFromToken.getId(), infoFromToken.getExpireTime()))) {
                throw new NonLoginException("用户 token为空！");
            }
            // jwt 的默认超时时间 + 用户的超时时间 是否在现在之前
            if (new DateTime(infoFromToken.getExpireTime()).plusMinutes(userAuthConfig.getTokenLimitExpire()).isBeforeNow()) {
                redisTemplate.opsForValue().set(RedisKeyUtil.buildUserDisableKey(infoFromToken.getId(), infoFromToken.getExpireTime()), "1");
                redisTemplate.delete(RedisKeyUtil.buildUserAbleKey(infoFromToken.getId(), infoFromToken.getExpireTime()));
                throw new NonLoginException("用户 token过期！");
            }
            return infoFromToken;
        } catch (ExpiredJwtException ex) {
            throw new NonLoginException("用户 token过期！");
        } catch (SignatureException ex) {
            throw new NonLoginException("用户签名错误！");
        } catch (IllegalArgumentException ex) {
            throw new NonLoginException("用户 token为空！");
        }
    }
}
