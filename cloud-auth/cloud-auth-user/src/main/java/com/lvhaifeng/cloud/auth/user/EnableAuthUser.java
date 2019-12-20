package com.lvhaifeng.cloud.auth.user;

import com.lvhaifeng.cloud.auth.client.EnableAuthClient;
import com.lvhaifeng.cloud.auth.user.configuration.AutoUserConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description 开启用户认证
 * @Author haifeng.lv
 * @Date 2019/12/16 17:32
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoUserConfiguration.class)
@Documented
@Inherited
@EnableAuthClient
public @interface EnableAuthUser {
}
