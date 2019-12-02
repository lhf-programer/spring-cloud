package com.lvhaifeng.cloud.auth.server.modules.oauth.biz;

import com.lvhaifeng.cloud.auth.server.modules.oauth.bean.OauthUser;
import com.lvhaifeng.cloud.auth.server.modules.oauth.entity.OauthClientDetails;
import com.lvhaifeng.cloud.auth.server.modules.oauth.mapper.OauthClientDetailsMapper;
import com.lvhaifeng.cloud.common.biz.BusinessBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
@Slf4j
public class OauthClientDetailsBiz extends BusinessBiz<OauthClientDetailsMapper, OauthClientDetails> {

    public OauthUser getLoginUserFullInfo(String id, String username, String password, String name, Collection<GrantedAuthority> authorities) {
        return new OauthUser(id, username, password, name, authorities);
    }

}
