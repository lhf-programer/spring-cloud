
package com.lvhaifeng.cloud.auth.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 跳过用户端 token
 * @Author haifeng.lv
 * @Date 2019/12/16 17:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreUserToken {
}
