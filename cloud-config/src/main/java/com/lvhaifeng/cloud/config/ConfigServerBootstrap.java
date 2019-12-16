package com.lvhaifeng.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Description 配置中心
 * @Author haifeng.lv
 * @Date 2019/12/16 17:50
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
