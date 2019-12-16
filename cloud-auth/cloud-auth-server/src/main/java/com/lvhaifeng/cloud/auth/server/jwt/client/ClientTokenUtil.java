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
     * @Description 秘钥加密token
     * @Author haifeng.lv
     * @param: jwtInfo
     * @Date 2019/12/16 17:34
     * @return: java.lang.String
     */
    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return jwtHelper.generateToken(jwtInfo, keyConfiguration.getServicePriKey(), expire);
    }

    /**
     * @Description 解析token
     * @Author haifeng.lv
     * @param: token
     * @Date 2019/12/16 17:34
     * @return: com.lvhaifeng.cloud.common.jwt.IJWTInfo
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
