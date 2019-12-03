
package com.lvhaifeng.cloud.common.util;


import com.lvhaifeng.cloud.common.constant.CommonConstants;
import com.lvhaifeng.cloud.common.constant.RedisKeyConstants;

import java.util.Date;

/**
 * @description
 * @author haifeng.lv
 * @updateTime 2019/11/30 11:24
 */
public class RedisKeyUtil {
    /**
     * @param userId
     * @param expire
     * @return
     */
    public static String buildUserAbleKey(String userId, Date expire) {
        return CommonConstants.REDIS_USER_TOKEN + RedisKeyConstants.USER_ABLE + userId + ":" + expire.getTime();
    }

    /**
     * @param userId
     * @param expire
     * @return
     */
    public static String buildUserDisableKey(String userId, Date expire) {
        // jwt:user_:dis: + userId + ":" + expire.getTime()
        return CommonConstants.REDIS_USER_TOKEN + RedisKeyConstants.USER_DISABLE + userId + ":" + expire.getTime();
    }
}
