package com.lvhaifeng.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @description 配置中心
 * @author haifeng.lv
 * @updateTime 2019/11/29 14:01
 */
@Slf4j
@EnableAutoConfiguration
@EnableConfigServer
public class ConfigServerBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerBootstrap.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "Application spring-could is running! \n\t" +
                "----------------------------------------------------------");
    }
}
