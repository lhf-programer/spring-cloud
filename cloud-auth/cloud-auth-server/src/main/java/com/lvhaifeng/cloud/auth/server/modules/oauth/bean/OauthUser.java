package com.lvhaifeng.cloud.auth.server.modules.oauth.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @description security 用户类
 * @author haifeng.lv
 * @updateTime 2019/12/12 17:17
 */
@Getter
@Setter
public class OauthUser extends User implements UserDetails {
    private static final long serialVersionUID = 3123152600328379950L;
    public String id;

    public OauthUser(String id, String username, String password, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

}
