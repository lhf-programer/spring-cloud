package com.lvhaifeng.cloud.auth.server.modules.user.entity;

import lombok.Data;

import java.util.Map;

/**
 * @Description 综合实体
 * @Author haifeng.lv
 * @Date 2019/12/16 17:37
 */
@Data
public class IntegrationAuthentication {
    private String authType;
    private String username;
    private Map<String,String[]> authParameters;
}
