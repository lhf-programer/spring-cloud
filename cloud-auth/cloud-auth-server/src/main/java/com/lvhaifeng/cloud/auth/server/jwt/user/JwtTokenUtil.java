package com.lvhaifeng.cloud.auth.server.jwt.user;

import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.jwt.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    public int getExpire() {
        return expire;
    }

    @Value("${jwt.expire}")
    private int expire;

    @Autowired
    private KeyConfiguration keyConfiguration;

    @Autowired
    private JWTHelper jwtHelper;

    public String generateToken(IJWTInfo jwtInfo, Map<String, String> otherInfo, Date expireTime) throws Exception {
        return jwtHelper.generateToken(jwtInfo, keyConfiguration.getUserPriKey(), expireTime, otherInfo);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        IJWTInfo infoFromToken = jwtHelper.getInfoFromToken(token, keyConfiguration.getUserPubKey());
        return infoFromToken;
    }
}
