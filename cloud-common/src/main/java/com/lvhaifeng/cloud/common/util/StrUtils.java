package com.lvhaifeng.cloud.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author haifeng.lv
 * @date 2018-04-27 下午2:11
 **/
public class StrUtils {
    public static final String EMPTY = "";

    public StrUtils() {
    }

    public static char getFirstCharIgnoreBlank(CharSequence cs) {
        int strLen;
        if(cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                char c = cs.charAt(i);
                if(!Character.isWhitespace(c)) {
                    return c;
                }
            }

            return '\u0000';
        } else {
            return '\u0000';
        }
    }

    public static String null2Empty(String value) {
        return value == null?"":value;
    }

    public static String urlDecode(String value, String charset) {
        try {
            return URLDecoder.decode(value, charset);
        } catch (UnsupportedEncodingException var3) {
            return value;
        }
    }
}
