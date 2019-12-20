package com.lvhaifeng.cloud.common.util;

/**
 * @Description string 工具类
 * @Author haifeng.lv
 * @Date 2019/12/18 16:31
 */
public class StringUtils {
    public static String getObjectValue(Object obj){
        return obj == null ? "":obj.toString();
    }
}