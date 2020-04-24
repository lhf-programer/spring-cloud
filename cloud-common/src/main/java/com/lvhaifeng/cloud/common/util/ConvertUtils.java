package com.lvhaifeng.cloud.common.util;

/**
 * @Description 字符串转换
 * @Author haifeng.lv
 * @Date 2020/1/10 15:53
 */
public class ConvertUtils {
    /**
     * @Description 将驼峰命名转化成下划线
     * @Author haifeng.lv
     * @param: para
     * @Date 2020/4/24 11:07
     * @return: java.lang.String
     */
    public static String camelToUnderline(String para) {
        if (para.length() < 3) {
            return para.toLowerCase();
        }
        StringBuilder sb = new StringBuilder(para);
        // 定位
        int temp = 0;
        // 从第三个字符开始 避免命名不规范
        for (int i = 2; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }

}
