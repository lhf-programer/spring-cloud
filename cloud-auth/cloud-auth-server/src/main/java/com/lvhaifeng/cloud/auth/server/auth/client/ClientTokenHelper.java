package com.lvhaifeng.cloud.auth.server.auth.client;

import com.lvhaifeng.cloud.auth.server.configuration.ClientConfiguration;
import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.auth.server.modules.client.service.impl.AuthClientServiceImpl;
import com.lvhaifeng.cloud.common.auth.AuthHelper;
import com.lvhaifeng.cloud.common.exception.auth.ClientTokenException;
import com.lvhaifeng.cloud.common.vo.AuthInfo;
import com.lvhaifeng.cloud.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @Description 客户端 token
 * @Author haifeng.lv
 * @Date 2019/12/19 10:40
 */
@Configuration
@Slf4j
public class ClientTokenHelper {
    @Autowired
    private KeyConfiguration keyConfiguration;
    @Autowired
    private ClientConfiguration clientConfiguration;
    @Autowired
    private AuthClientServiceImpl authClientService;

    /**
     * @Description 秘钥加密token
     * @Author haifeng.lv
     * @param: authInfo 授权信息
     * @Date 2019/12/16 17:34
     * @return: java.lang.String
     */
    public String generateToken(AuthInfo authInfo) throws Exception {
        return AuthHelper.generateToken(authInfo, keyConfiguration.getClientPriKey(), clientConfiguration.getExpire());
    }

    /**
     * @Description 验证客户端 token
     * @Author haifeng.lv
     * @Date 2019/12/20 11:47
     */
    public boolean verifyClientToken(String token, String clientId) throws InvalidKeySpecException, NoSuchAlgorithmException {
        // 获取 jwt基本信息
        AuthInfo authInfo = AuthHelper.getInfoFromToken(token, keyConfiguration.getClientPubKey());
        LocalDateTime expireTime = authInfo.getExpireTime();
        if (Duration.between(expireTime, LocalDateTime.now()).toMillis() > 0) {
            throw new ClientTokenException("客户端 token过期！");
        }
        String id = authInfo.getId();
        // 获取所有允许的 client
        for(String client: authClientService.findAllowedClient(clientId)) {
            if(client.equals(id)){
                return true;
            }
        }
        return false;
    }

}
