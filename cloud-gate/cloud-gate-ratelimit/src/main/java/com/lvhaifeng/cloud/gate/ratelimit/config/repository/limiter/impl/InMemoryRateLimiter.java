package com.lvhaifeng.cloud.gate.ratelimit.config.repository.limiter.impl;

import com.lvhaifeng.cloud.gate.ratelimit.config.Rate;
import com.lvhaifeng.cloud.gate.ratelimit.config.repository.limiter.AbstractRateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 内存速率限流器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:51
 */
public class InMemoryRateLimiter extends AbstractRateLimiter {
    private Map<String, Rate> repository = new ConcurrentHashMap<>();

    @Override
    protected Rate getRate(String key) {
        return this.repository.get(key);
    }

    @Override
    protected void saveRate(Rate rate) {
        this.repository.put(rate.getKey(), rate);
    }
}
