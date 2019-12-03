package com.lvhaifeng.cloud.admin;

import com.lvhaifeng.cloud.auth.client.EnableAuthClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author haifeng.lv
 * @date 2019-07-10 18:07
 */
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
@EnableFeignClients({"com.lvhaifeng.cloud.auth.client.feign", "com.lvhaifeng.cloud.admin.feign"})
@EnableTransactionManagement
@MapperScan("com.lvhaifeng.cloud.admin.mapper")
@EnableAuthClient
public class AdminBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminBootstrap.class).web(true).run(args);
    }
}
