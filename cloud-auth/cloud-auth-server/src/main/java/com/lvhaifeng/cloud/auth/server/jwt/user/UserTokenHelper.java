package com.lvhaifeng.cloud.auth.server.jwt.user;

import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.jwt.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Description 用户 token
 * @Author haifeng.lv
 * @Date 2019/12/19 10:41
 */
@Component
public class UserTokenHelper {
    public int getExpire() {
        return expire;
    }

    @Value("${jwt.expire}")
    private int expire;

    @Autowired
    private KeyConfiguration keyConfiguration;

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        IJWTInfo infoFromToken = JWTHelper.getInfoFromToken(token, keyConfiguration.getUserPubKey());
        return infoFromToken;
    }
}
