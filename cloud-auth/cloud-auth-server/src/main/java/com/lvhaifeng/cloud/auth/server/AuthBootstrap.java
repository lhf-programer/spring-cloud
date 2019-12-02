package com.lvhaifeng.cloud.auth.server;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.SessionAttributes;

@SpringBootApplication(scanBasePackages = {"com.lvhaifeng.cloud.auth.server", "com.lvhaifeng.cloud.common"})
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.lvhaifeng.cloud.auth.server.modules.*.mapper")
@SessionAttributes("authorizationRequest")
@EnableResourceServer
@EnableAuthorizationServer
public class AuthBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(AuthBootstrap.class, args);
    }
}
