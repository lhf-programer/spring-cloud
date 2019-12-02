package com.lvhaifeng.cloud.gate.config;

import com.lvhaifeng.cloud.gate.ratelimit.config.IUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户权限校验
 *
 * @author haifeng.lv
 * @date 2019-07-29 13:47
 */
public class UserPrincipal implements IUserPrincipal {

    @Override
    public String getName(HttpServletRequest request) {
        return null;
    }

}
