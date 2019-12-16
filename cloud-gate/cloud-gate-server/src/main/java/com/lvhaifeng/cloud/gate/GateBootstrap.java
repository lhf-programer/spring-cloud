package com.lvhaifeng.cloud.gate;

import com.lvhaifeng.cloud.auth.client.EnableAuthClient;
import com.lvhaifeng.cloud.gate.config.UserPrincipal;
import com.lvhaifeng.cloud.gate.ratelimit.EnableGateRateLimit;
import com.lvhaifeng.cloud.gate.ratelimit.config.IUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description 网关
 * @Author haifeng.lv
 * @Date 2019/12/16 17:55
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
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
