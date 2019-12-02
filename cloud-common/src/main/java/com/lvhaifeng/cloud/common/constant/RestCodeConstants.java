package com.lvhaifeng.cloud.common.constant;

/**
 * @description rest接口返回码
 * @author haifeng.lv
 * @updateTime 2019/11/30 11:04
 */
public class RestCodeConstants {
    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 40101;
    public static final Integer EX_USER_PASS_INVALID_CODE = 40001;
    public static final Integer EX_USER_FORBIDDEN_CODE = 40131;
    // 客户端token异常
    public static final Integer EX_CLIENT_INVALID_CODE = 40301;
    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40331;
    // 业务异常返回码
    public static final Integer EX_BUSINESS_BASE_CODE = 30101;
}
