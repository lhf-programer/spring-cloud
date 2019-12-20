package com.lvhaifeng.cloud.auth.server.modules.client.vo;

import com.lvhaifeng.cloud.common.jwt.IJWTInfo;

import java.util.Date;
import java.util.Map;

/**
 * @Description 客户端信息
 * @Author haifeng.lv
 * @Date 2019/12/16 17:35
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
