package com.lvhaifeng.cloud.auth.server.modules.oauth.service;

import com.lvhaifeng.cloud.auth.server.feign.IUserFeign;
import com.lvhaifeng.cloud.auth.server.modules.oauth.bean.OauthUser;
import com.lvhaifeng.cloud.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class OauthUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserFeign iUserFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }
        ObjectRestResponse<Map<String, String>> response = iUserFeign.getUserInfoByUsername(username);
        Map<String, String> data = response.getData();
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new OauthUser(data.get("id"), data.get("loginName"), data.get("password"), data.get("realName"), authorities);
    }
}
