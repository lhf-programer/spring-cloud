package com.lvhaifeng.cloud.auth.client;

import com.lvhaifeng.cloud.auth.client.configuration.AutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description 开启认证
 * @Author haifeng.lv
 * @Date 2019/12/16 17:32
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfiguration.class)
@Documented
@Inherited
public @interface EnableAuthClient {
}
