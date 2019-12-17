package com.lvhaifeng.cloud.common.util;

import com.lvhaifeng.cloud.common.constant.CommonKeyConstants;
import com.lvhaifeng.cloud.common.constant.RedisKeyConstants;

import java.util.Date;

/**
 * @Description redis key 工具类
 * @Author haifeng.lv
 * @Date 2019/12/16 17:46
 */
public class RedisKeyUtil {

    /**
     * @Description 构建用户可用 key
     * @Author haifeng.lv
     * @param: userId
     * @param: expire 存活时间
     * @Date 2019/12/16 17:47
     * @return: java.lang.String
     */
    public static String buildUserAbleKey(String userId, Date expire) {
        return CommonKeyConstants.REDIS_USER_TOKEN + RedisKeyConstants.USER_ABLE + userId + ":" + expire.getTime();
    }

    /**
     * @Description 构建用户不可用 key
     * @Author haifeng.lv
     * @param: userId
     * @param: expire 存活时间
     * @Date 2019/12/16 17:47
     * @return: java.lang.String
     */
    public static String buildUserDisableKey(String userId, Date expire) {
        // jwt:user_:dis: + userId + ":" + expire.getTime()
        return CommonKeyConstants.REDIS_USER_TOKEN + RedisKeyConstants.USER_DISABLE + userId + ":" + expire.getTime();
    }
}
