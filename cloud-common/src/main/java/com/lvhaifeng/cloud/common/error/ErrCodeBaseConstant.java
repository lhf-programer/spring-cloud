package com.lvhaifeng.cloud.common.error;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/1/6 10:33
 */
public class ErrCodeBaseConstant {
    /**
     * 通用，参数错误；只用错误码、不用错误消息
     */
    public static final ErrorCode COMMON_PARAM_ERR = new ErrorCode("10000", "参数错误");
    /**
     * 没有查询到该资源
     */
    public static final ErrorCode NO_RESOURCE_ERR = new ErrorCode("20001", "没有查询到该资源");
}
