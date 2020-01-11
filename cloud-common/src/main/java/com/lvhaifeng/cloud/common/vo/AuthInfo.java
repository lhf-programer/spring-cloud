package com.lvhaifeng.cloud.common.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Description 授权基本信息类
 * @Author haifeng.lv
 * @Date 2019/12/20 17:11
 */
@Data
public class AuthInfo {
    private String id;
    private String name;
    private LocalDateTime expireTime;
    private String code;
    private Map<String, String> otherInfo;

    public AuthInfo() {
    }

    public AuthInfo(String id, String name, LocalDateTime expireTime, String code, Map<String, String> otherInfo) {
        this.id = id;
        this.name = name;
        this.expireTime = expireTime;
        this.code = code;
        this.otherInfo = otherInfo;
    }

    public AuthInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
