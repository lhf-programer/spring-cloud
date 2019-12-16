package com.lvhaifeng.cloud.gate.config;

import com.lvhaifeng.cloud.gate.ratelimit.config.IUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 用户权限校验
 * @Author haifeng.lv
 * @Date 2019/12/16 17:53
 */
public class UserPrincipal implements IUserPrincipal {
    @Override
    public String getName(HttpServletRequest request) {
        return null;
    }
}
