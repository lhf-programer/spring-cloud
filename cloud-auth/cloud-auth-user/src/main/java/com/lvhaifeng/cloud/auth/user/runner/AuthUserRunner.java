package com.lvhaifeng.cloud.auth.user.runner;

import com.lvhaifeng.cloud.auth.client.config.AuthClientConfig;
import com.lvhaifeng.cloud.auth.user.config.AuthUserConfig;
import com.lvhaifeng.cloud.auth.user.feign.IAuthUserFeign;
import com.lvhaifeng.cloud.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Description runner 初始化
 * @Author haifeng.lv
 * @Date 2019/12/16 17:32
 */
@Configuration
@Slf4j
public class AuthUserRunner implements CommandLineRunner {
    @Autowired
    private AuthUserConfig authUserConfig;
    @Autowired
    private IAuthUserFeign authUserFeign;
    @Autowired
    private AuthClientConfig authClientConfig;

    @Override
    public void run(String... args) {
        log.info("初始化加载用户pubKey");
        try {
            log.info("refreshUserPubKey,clientId={},clientSecret={}",authClientConfig.getClientId(),authClientConfig.getClientSecret());
            refreshUserPubKey();
        } catch (Exception e) {
            log.error("初始化加载用户pubKey失败,1分钟后自动重试!", e);
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void refreshUserPubKey() {
        log.info("refreshUserPubKey,clientId={},clientSecret={}",authClientConfig.getClientId(),authClientConfig.getClientSecret());
        Result<byte[]> userPublicKey = authUserFeign.getUserPublicKey(authClientConfig.getClientId(), authClientConfig.getClientSecret());
        if (userPublicKey.isSuccess()) {
            authUserConfig.setPubKeyByte(userPublicKey.getResult());
        }
    }

}
