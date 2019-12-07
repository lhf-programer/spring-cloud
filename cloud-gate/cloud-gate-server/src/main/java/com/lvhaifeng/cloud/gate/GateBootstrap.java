package com.lvhaifeng.cloud.gate;

import com.lvhaifeng.cloud.auth.client.EnableAuthClient;
import com.lvhaifeng.cloud.gate.config.UserPrincipal;
import com.lvhaifeng.cloud.gate.ratelimit.EnableGateRateLimit;
import com.lvhaifeng.cloud.gate.ratelimit.config.IUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 网关
 * @author haifeng.lv
 * @date 2019-07-29 13:50
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableZuulProxy
@EnableFeignClients({"com.lvhaifeng.cloud.auth.client.feign"})
@EnableScheduling
@EnableAuthClient
@EnableGateRateLimit
public class GateBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(GateBootstrap.class, args);
        log.info("\n----------------------------------------------------------\n\t" +
                "Application spring-could is running! \n\t" +
                "----------------------------------------------------------");
    }

    @Bean
    @Primary
    IUserPrincipal userPrincipal() {
        return new UserPrincipal();
    }
}
