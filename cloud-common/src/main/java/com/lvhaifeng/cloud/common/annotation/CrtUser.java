package com.lvhaifeng.cloud.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 创建用户
 * @Author haifeng.lv
 * @Date 2019/12/16 17:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
public @interface CrtUser {
}
