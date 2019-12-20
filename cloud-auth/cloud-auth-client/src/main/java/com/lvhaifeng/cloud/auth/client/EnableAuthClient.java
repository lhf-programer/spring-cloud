package com.lvhaifeng.cloud.auth.client;

import com.lvhaifeng.cloud.auth.client.configuration.AutoClientConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description 开启客户端认证
 * @Author haifeng.lv
 * @Date 2019/12/16 17:32
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoClientConfiguration.class)
@Documented
@Inherited
public @interface EnableAuthClient {
}
