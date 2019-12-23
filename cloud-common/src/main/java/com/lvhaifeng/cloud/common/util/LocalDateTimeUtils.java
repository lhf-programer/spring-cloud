package com.lvhaifeng.cloud.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @Description 时间工具类
 * @Author haifeng.lv
 * @Date 2019/12/23 14:53
 */
public class LocalDateTimeUtils {

    /**
     * LocalDateTime转换为Date
     * @param localDateTime
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
}
