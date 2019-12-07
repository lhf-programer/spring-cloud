package com.lvhaifeng.cloud.center;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class CenterBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(CenterBootstrap.class, args);
        Environment env = application.getEnvironment();
        String port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application spring-could is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "----------------------------------------------------------");
    }
}
