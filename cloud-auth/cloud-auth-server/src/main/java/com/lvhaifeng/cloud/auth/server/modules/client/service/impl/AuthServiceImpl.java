package com.lvhaifeng.cloud.auth.server.modules.client.service.impl;

import com.lvhaifeng.cloud.auth.server.feign.IUserFeign;
import com.lvhaifeng.cloud.auth.server.jwt.user.JwtTokenUtil;
import com.lvhaifeng.cloud.auth.server.modules.client.service.AuthService;
import com.lvhaifeng.cloud.common.exception.auth.UserInvalidException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.jwt.JWTInfo;
import com.lvhaifeng.cloud.common.util.RedisKeyUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haifeng.lv
 */
@Service
public class AuthServiceImpl implements AuthService {

    private JwtTokenUtil jwtTokenUtil;
    private IUserFeign iUserFeign;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public AuthServiceImpl(
            JwtTokenUtil jwtTokenUtil,
            IUserFeign iUserFeign) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.iUserFeign = iUserFeign;
    }

    @Override
    public String login(String username, String password) throws Exception {
        Map<String, String> data = iUserFeign.validate(username, password).getData();
        String token;
        if (!StringUtils.isEmpty(data.get("id"))) {
            Map<String, String> map = new HashMap<>();
            JWTInfo jwtInfo = new JWTInfo(data.get("username"), data.get("id"), data.get("name"));
            Date expireTime = DateTime.now().plusSeconds(jwtTokenUtil.getExpire()).toDate();
            token = jwtTokenUtil.generateToken(jwtInfo, map, expireTime);
            redisTemplate.opsForValue().set(RedisKeyUtil.buildUserAbleKey(data.get("id"), expireTime), "1");
            return token;
        }
        throw new UserInvalidException("用户不存在或账户密码错误!");
    }

    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);
    }

    @Override
    public Boolean invalid(String token) throws Exception {
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(token);
        redisTemplate.delete(RedisKeyUtil.buildUserAbleKey(infoFromToken.getId(), infoFromToken.getExpireTime()));
        redisTemplate.opsForValue().set(RedisKeyUtil.buildUserDisableKey(infoFromToken.getId(), infoFromToken.getExpireTime()), "1");
        return true;
    }

    @Override
    public String refresh(String oldToken) throws Exception {
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(oldToken);
        invalid(oldToken);
        Date expireTime = DateTime.now().plusSeconds(jwtTokenUtil.getExpire()).toDate();
        redisTemplate.opsForValue().set(RedisKeyUtil.buildUserAbleKey(infoFromToken.getId(), expireTime), "1");
        return jwtTokenUtil.generateToken(infoFromToken, infoFromToken.getOtherInfo(), expireTime);
    }
}
