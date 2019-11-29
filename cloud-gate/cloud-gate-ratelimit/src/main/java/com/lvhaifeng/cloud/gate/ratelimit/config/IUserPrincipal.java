package com.lvhaifeng.cloud.gate.ratelimit.config;

import javax.servlet.http.HttpServletRequest;

public interface IUserPrincipal {
    String getName(HttpServletRequest request);
}
