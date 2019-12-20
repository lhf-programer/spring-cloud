package com.lvhaifeng.cloud.auth.server.jwt.client;

import com.lvhaifeng.cloud.auth.server.configuration.ClientConfiguration;
import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import com.lvhaifeng.cloud.common.exception.auth.ClientTokenException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.jwt.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

/**
 * @Description 客户端 token
 * @Author haifeng.lv
 * @Date 2019/12/19 10:40
 */
@Configuration
public class ClientTokenHelper {
    @Value("${client.expire}")
    private int expire;
    @Autowired
    private KeyConfiguration keyConfiguration;
    @Autowired
    private IAuthClientService authClientService;

    /**
     * @Description 秘钥加密token
     * @Author haifeng.lv
     * @param: jwtInfo
     * @Date 2019/12/16 17:34
     * @return: java.lang.String
     */
    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return JWTHelper.generateToken(jwtInfo, keyConfiguration.getServicePriKey(), expire);
    }

    /**
     * @Description 解析token
     * @Author haifeng.lv
     * @param: token
     * @Date 2019/12/16 17:34
     * @return: com.lvhaifeng.cloud.common.jwt.IJWTInfo
     */
    public IJWTInfo getInfoFromToken(String token) throws InvalidKeySpecException, NoSuchAlgorithmException {
        IJWTInfo infoFromToken = JWTHelper.getInfoFromToken(token, keyConfiguration.getServicePubKey());
        Date current = infoFromToken.getExpireTime();
        if (new Date().after(current)) {
            throw new ClientTokenException("客户端 token过期！");
        }
        return infoFromToken;
    }

    /**
     * @Description 验证客户端 token
     * @Author haifeng.lv
     * @Date 2019/12/20 11:47
     */
    public boolean verifyClientToken(String token, String clientId) throws InvalidKeySpecException, NoSuchAlgorithmException {
        // 获取 jwt基本信息
        IJWTInfo infoFromToken = getInfoFromToken(token);
        String uniqueName = infoFromToken.getUniqueName();
        // 获取所有允许的 client
        for(String client: authClientService.getAllowedClient(clientId)){
            if(client.equals(uniqueName)){
                return true;
            }
        }
        return false;
    }
}
