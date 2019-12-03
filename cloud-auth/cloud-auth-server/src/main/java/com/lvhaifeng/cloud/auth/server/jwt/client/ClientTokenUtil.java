

package com.lvhaifeng.cloud.auth.server.jwt.client;

import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.common.exception.auth.ClientTokenException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.jwt.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class ClientTokenUtil {
    @Value("${client.expire}")
    private int expire;
    @Autowired
    private JWTHelper jwtHelper;
    @Autowired
    private KeyConfiguration keyConfiguration;

    /**
     * 秘钥加密token
     *
     * @author haifeng.lv
     * @date 2019-08-07 14:28
     */
    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return jwtHelper.generateToken(jwtInfo, keyConfiguration.getServicePriKey(), expire);
    }

    /**
     * 解析token
     *
     * @author haifeng.lv
     * @date 2019-08-07 14:28
     */
    public IJWTInfo getInfoFromToken(String token) throws Exception {
        IJWTInfo infoFromToken = jwtHelper.getInfoFromToken(token, keyConfiguration.getServicePubKey());
        Date current = infoFromToken.getExpireTime();
        if (new Date().after(current)) {
            throw new ClientTokenException("客户端 token过期！");
        }
        return infoFromToken;
    }
}
