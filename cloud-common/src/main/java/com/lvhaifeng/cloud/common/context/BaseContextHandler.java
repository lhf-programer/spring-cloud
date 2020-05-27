package com.lvhaifeng.cloud.common.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 后台当前上下文
 * @Author haifeng.lv
 * @Date 2019/12/16 17:42
 */
public class BaseContextHandler {
    public static final String CONTEXT_KEY_USER_ID = "currentUserId";
    public static final String CONTEXT_KEY_USER_NAME = "currentUserName";
    public static final String CONTEXT_KEY_USER_TOKEN = "currentUserToken";
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(20);
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static String getUserId() {
        Object value = get(CONTEXT_KEY_USER_ID);
        return returnObjectValue(value);
    }

    public static String getUserName() {
        Object value = get(CONTEXT_KEY_USER_NAME);
        return returnObjectValue(value);
    }

    public static String getToken() {
        Object value = get(CONTEXT_KEY_USER_TOKEN);
        return value == null ? "" : value.toString();
    }

    public static void setToken(String token) {
        set(CONTEXT_KEY_USER_TOKEN, token);
    }

    public static void setUserId(String userId) {
        set(CONTEXT_KEY_USER_ID, userId);
    }

    public static void setUserName(String userName) {
        set(CONTEXT_KEY_USER_NAME, userName);
    }

    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
