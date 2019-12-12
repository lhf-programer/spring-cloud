package com.lvhaifeng.cloud.auth.server.modules.client.bean;


import com.lvhaifeng.cloud.common.jwt.IJWTInfo;

import java.util.Date;
import java.util.Map;

/**
 * 客户端信息
 * @author haifeng.lv
 * @date 2019-08-03 13:51
 */
public class ClientInfo implements IJWTInfo {
    String clientId;
    String name;
    String id;

    public ClientInfo(String clientId, String name, String id) {
        this.clientId = clientId;
        this.name = name;
        this.id = id;
    }

    @Override
    public String getUniqueName() {
        return clientId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getExpireTime() {
        return null;
    }

    @Override
    public Map<String, String> getOtherInfo() {
        return null;
    }
}
