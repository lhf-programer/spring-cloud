package com.lvhaifeng.cloud.gate.ratelimit.config;


import com.lvhaifeng.cloud.gate.ratelimit.config.properties.RateLimitProperties.Policy;

/**
 * 限流
 *
 * @author haifeng.lv
 * @date 2019-07-29 13:52
 */
public interface RateLimiter {
    Rate consume(Policy policy, String key);
}
