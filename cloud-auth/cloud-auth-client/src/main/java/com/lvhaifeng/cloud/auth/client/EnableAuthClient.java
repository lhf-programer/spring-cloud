package com.lvhaifeng.cloud.auth.client;

import com.lvhaifeng.cloud.auth.client.configuration.AutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Classname EnableAuthClient
 * @Description 开启认证
 * @Date 2019/11/30 10:07
 * @Author haifeng.lv
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfiguration.class)
@Documented
@Inherited
public @interface EnableAuthClient {
}
