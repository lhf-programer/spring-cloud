package com.lvhaifeng.cloud.common.util;

import java.util.Base64;

/**
 * @Description 编码工具类
 * @Author haifeng.lv
 * @Date 2019/12/18 16:35
 */
public class EncoderUtils {
    public static String toHexString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static final byte[] toBytes(String str) {
        return Base64.getDecoder().decode(str);
    }
}
