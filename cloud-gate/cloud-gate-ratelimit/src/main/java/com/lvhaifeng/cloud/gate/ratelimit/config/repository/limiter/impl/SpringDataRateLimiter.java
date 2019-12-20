package com.lvhaifeng.cloud.gate.ratelimit.config.repository.limiter.impl;

import com.lvhaifeng.cloud.gate.ratelimit.config.Rate;
import com.lvhaifeng.cloud.gate.ratelimit.config.repository.limiter.AbstractRateLimiter;
import com.lvhaifeng.cloud.gate.ratelimit.config.repository.springdata.IRateLimiterRepository;
import lombok.RequiredArgsConstructor;

/**
 * @Description 数据库限流器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:51
 */
@RequiredArgsConstructor
public class SpringDataRateLimiter extends AbstractRateLimiter {
    private final IRateLimiterRepository repository;

    @Override
    protected Rate getRate(String key) {
        return this.repository.findById(key).get();
    }

    @Override
    protected void saveRate(Rate rate) {
        this.repository.save(rate);
    }
}
