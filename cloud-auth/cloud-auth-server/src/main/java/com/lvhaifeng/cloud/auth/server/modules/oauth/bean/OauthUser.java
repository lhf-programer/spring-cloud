package com.lvhaifeng.cloud.auth.server.modules.oauth.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class OauthUser extends User implements UserDetails {
    private static final long serialVersionUID = 3123152600328379950L;
    public String id;
    public String name;

    public OauthUser(String id, String username, String password, String name, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.name = name;
    }

}
