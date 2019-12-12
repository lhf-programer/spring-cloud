package com.lvhaifeng.cloud.auth.client.runner;

import com.lvhaifeng.cloud.auth.client.config.ServiceAuthConfig;
import com.lvhaifeng.cloud.auth.client.config.UserAuthConfig;
import com.lvhaifeng.cloud.auth.client.feign.ServiceAuthFeign;
import com.lvhaifeng.cloud.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * runner 初始化
 * @author haifeng.lv
 * @version 2018/11/29.
 */
@Configuration
@Slf4j
public class AuthClientRunner implements CommandLineRunner {
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;
    @Autowired
    private UserAuthConfig userAuthConfig;
    @Autowired
    private ServiceAuthFeign serviceAuthFeign;

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化加载用户pubKey");
        try {
            log.info("refreshUserPubKey,clientId={},clientSecret={}",serviceAuthConfig.getClientId(),serviceAuthConfig.getClientSecret());
            refreshUserPubKey();
        } catch (Exception e) {
            log.error("初始化加载用户pubKey失败,1分钟后自动重试!", e);
        }
        log.info("初始化加载客户pubKey");
        try {
            log.info("refreshServicePubKey,clientId={},clientSecret={}",serviceAuthConfig.getClientId(),serviceAuthConfig.getClientSecret());
            refreshServicePubKey();
        } catch (Exception e) {
            log.error("初始化加载客户pubKey失败,1分钟后自动重试!", e);
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void refreshUserPubKey() {
        log.info("refreshUserPubKey,clientId={},clientSecret={}",serviceAuthConfig.getClientId(),serviceAuthConfig.getClientSecret());
        Result<byte[]> userPublicKey = serviceAuthFeign.getUserPublicKey(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (userPublicKey.isSuccess()) {
            this.userAuthConfig.setPubKeyByte(userPublicKey.getResult());
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void refreshServicePubKey() {
        log.info("refreshServicePubKey,clientId={},clientSecret={}",serviceAuthConfig.getClientId(),serviceAuthConfig.getClientSecret());
        Result<byte[]> servicePublicKey = serviceAuthFeign.getServicePublicKey(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (servicePublicKey.isSuccess()) {
            this.serviceAuthConfig.setPubKeyByte(servicePublicKey.getResult());
        }
    }
}
