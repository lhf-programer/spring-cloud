package com.lvhaifeng.cloud.common.query;

import com.lvhaifeng.cloud.common.util.StringUtils;

/**
 * @Description 查询链接规则
 * @Author haifeng.lv
 * @Date 2020/1/10 16:09
 */
public enum MatchTypeEnum {
    AND("AND"),
    OR("OR");

    private String value;

    MatchTypeEnum(String value) {
        this.value = value;
    }

    public static MatchTypeEnum getByValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (MatchTypeEnum val : values()) {
            if (val.getValue().toLowerCase().equals(value.toLowerCase())) {
                return val;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
