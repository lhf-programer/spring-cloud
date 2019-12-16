package com.lvhaifeng.cloud.common.constant;

import java.time.format.DateTimeFormatter;

/**
 * @Description 时间格式
 * @Author haifeng.lv
 * @Date 2019/12/16 17:41
 */
public interface DateFormatters {
    DateTimeFormatter STANDARD_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter STANDARD_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter STANDARD_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
}
