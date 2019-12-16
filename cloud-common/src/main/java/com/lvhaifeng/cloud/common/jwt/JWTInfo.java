package com.lvhaifeng.cloud.common.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Description jwt 基本信息
 * @Author haifeng.lv
 * @Date 2019/12/16 17:45
 */
public class JWTInfo implements Serializable, IJWTInfo {
    private static final long serialVersionUID = 5452605590172369563L;
    private String username;
    private String userId;
    private String name;
    private Date expireTime;
    private Map<String,String> otherInfo;

    public JWTInfo(String username, String userId, String name, Date expireTime) {
        this.username = username;
        this.userId = userId;
        this.name = name;
        this.expireTime = expireTime;
    }

    public JWTInfo(String username, String userId, String name, Date expireTime, Map<String,String> otherInfo) {
        this.username = username;
        this.userId = userId;
        this.name = name;
        this.expireTime = expireTime;
        this.otherInfo = otherInfo;
    }

    public JWTInfo(String username, String userId, String name) {
        this.username = username;
        this.userId = userId;
        this.name = name;
    }

    @Override
    public String getUniqueName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getExpireTime() {
        return expireTime;
    }

    @Override
    public Map<String, String> getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(Map<String, String> otherInfo) {
        this.otherInfo = otherInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JWTInfo jwtInfo = (JWTInfo) o;

        if (username != null ? !username.equals(jwtInfo.username) : jwtInfo.username != null) {
            return false;
        }
        return userId != null ? userId.equals(jwtInfo.userId) : jwtInfo.userId == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
