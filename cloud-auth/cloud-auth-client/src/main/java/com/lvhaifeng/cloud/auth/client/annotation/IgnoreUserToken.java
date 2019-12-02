
package com.lvhaifeng.cloud.auth.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 跳过用户端 token
 * @author haifeng.lv
 * @updateTime 2019/11/30 10:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreUserToken {
}
