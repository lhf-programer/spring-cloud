package com.lvhaifeng.cloud.auth.server.modules.user.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Description security 用户类
 * @Author haifeng.lv
 * @Date 2019/12/16 17:37
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
