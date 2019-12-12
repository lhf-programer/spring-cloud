package com.lvhaifeng.cloud.auth.server.modules.oauth.entity;

import lombok.Data;

import java.util.Map;

/**
 * 综合实体
 * @author haifeng.lv
 * @date 2018-3-30
 **/
@Data
public class IntegrationAuthentication {
    private String authType;
    private String username;
    private Map<String,String[]> authParameters;
}
