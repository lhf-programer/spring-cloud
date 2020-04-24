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

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if ("".equals(object)) {
            return true;
        }
        if ("null".equals(object)) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object object) {
        if (object != null && !object.equals("") && !object.equals("null")) {
            return (true);
        }
        return (false);
    }
}