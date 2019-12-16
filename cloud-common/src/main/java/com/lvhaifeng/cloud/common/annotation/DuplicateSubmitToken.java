package com.lvhaifeng.cloud.common.annotation;

import java.lang.annotation.*;

/**
 * @Description 防止表单重复提交
 * @Author haifeng.lv
 * @Date 2019/12/16 17:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface DuplicateSubmitToken {

    /**
     * 一次请求完成之前防止重复提交
     */
    public static final int REQUEST = 1;

    /**
     * redis key 的默认过期时间为 5s
     */
    public static final int TIME = 5;

    /**
     * 一次会话中防止重复提交
     */
    public static final int SESSION = 2;

    /**
     * 保存重复提交标记 默认为需要保存
     */
    boolean save() default true;

    /**
     * 防止重复提交类型，默认：一次请求完成之前防止重复提交
     */
    int type() default REQUEST;

    /**
     * 提交保存到redis 中key的过期时间
     */
    int submitTime() default TIME;

}
