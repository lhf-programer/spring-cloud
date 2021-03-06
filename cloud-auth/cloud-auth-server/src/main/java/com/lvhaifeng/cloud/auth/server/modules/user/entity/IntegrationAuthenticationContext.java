package com.lvhaifeng.cloud.auth.server.modules.user.entity;

/**
 * @Description 综合实体环境上下文
 * @Author haifeng.lv
 * @Date 2019/12/16 17:37
 */
public class IntegrationAuthenticationContext {
    private static ThreadLocal<IntegrationAuthentication> holder = new ThreadLocal<>();

    public static void set(IntegrationAuthentication integrationAuthentication){
        holder.set(integrationAuthentication);
    }

    public static IntegrationAuthentication get(){
        return holder.get();
    }

    public static void clear(){
        holder.remove();
    }
}
