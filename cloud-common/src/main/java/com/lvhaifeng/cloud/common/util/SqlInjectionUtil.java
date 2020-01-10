package com.lvhaifeng.cloud.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description sql注入处理工具类
 * @Author haifeng.lv
 * @Date 2020/1/10 15:55
 */
@Slf4j
public class SqlInjectionUtil {
    final static String xssStr = "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+|,";

    /**
     * @Description sql注入过滤处理，遇到注入关键字抛异常
     * @Author haifeng.lv
     * @param: value
     * @Date 2020/1/10 15:55
     */
    public static void filterContent(String value) {
        if (value == null || "".equals(value)) {
            return;
        }
        // 统一转为小写
        value = value.toLowerCase();
        String[] xssArr = xssStr.split("\\|");
        for (int i = 0; i < xssArr.length; i++) {
            if (value.indexOf(xssArr[i]) > -1) {
                log.error("请注意，值可能存在SQL注入风险!---> {}", value);
                throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
            }
        }
        return;
    }

    /**
     * @Description 仅用于字典条件SQL参数，注入过滤
     * @Author haifeng.lv
     * @param: value
     * @Date 2020/1/10 15:55
     */
    @Deprecated
    public static void specialFilterContent(String value) {
        String specialXssStr = "exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|+|";
        String[] xssArr = specialXssStr.split("\\|");
        if (value == null || "".equals(value)) {
            return;
        }
        // 统一转为小写
        value = value.toLowerCase();
        for (int i = 0; i < xssArr.length; i++) {
            if (value.indexOf(xssArr[i]) > -1) {
                log.error("请注意，值可能存在SQL注入风险!---> {}", value);
                throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
            }
        }
        return;
    }

    /**
     * @Description 仅用于Online报表SQL解析，注入过滤
     * @Author haifeng.lv
     * @param: value
     * @Date 2020/1/10 15:56
     */
    @Deprecated
    public static void specialFilterContentForOnlineReport(String value) {
        String specialXssStr = "exec |insert |delete |update |drop |chr |mid |master |truncate |char |declare |";
        String[] xssArr = specialXssStr.split("\\|");
        if (value == null || "".equals(value)) {
            return;
        }
        // 统一转为小写
        value = value.toLowerCase();
        for (int i = 0; i < xssArr.length; i++) {
            if (value.indexOf(xssArr[i]) > -1) {
                log.error("请注意，值可能存在SQL注入风险!---> {}", value);
                throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
            }
        }
        return;
    }

}
