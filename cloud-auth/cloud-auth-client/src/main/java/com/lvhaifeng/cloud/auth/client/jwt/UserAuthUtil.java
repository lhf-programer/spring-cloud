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
                throw new NonLoginException("User token is empty!");
            }
            if (new DateTime(infoFromToken.getExpireTime()).plusMinutes(userAuthConfig.getTokenLimitExpire()).isBeforeNow()) {
                redisTemplate.opsForValue().set(RedisKeyUtil.buildUserDisableKey(infoFromToken.getId(), infoFromToken.getExpireTime()), "1");
                redisTemplate.delete(RedisKeyUtil.buildUserAbleKey(infoFromToken.getId(), infoFromToken.getExpireTime()));
                throw new NonLoginException("User token expired!");
            }
            return infoFromToken;
        } catch (ExpiredJwtException ex) {
            throw new NonLoginException("User token expired!");
        } catch (SignatureException ex) {
            throw new NonLoginException("User token signature error!");
        } catch (IllegalArgumentException ex) {
            throw new NonLoginException("User token is empty!");
        }
    }
}
