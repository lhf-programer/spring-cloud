package com.lvhaifeng.cloud.gate.ratelimit.config.repository.springdata;

import com.lvhaifeng.cloud.gate.ratelimit.config.Rate;
import com.lvhaifeng.cloud.gate.ratelimit.config.repository.AbstractRateLimiter;
import lombok.RequiredArgsConstructor;

/**
 * 在内存速率限制器配置为开发环境
 *
 * @author haifeng.lv
 * @since 2018-06-23
 */
@RequiredArgsConstructor
public class SpringDataRateLimiter extends AbstractRateLimiter {

    private final IRateLimiterRepository repository;

    @Override
    protected Rate getRate(String key) {
        return this.repository.findOne(key);
    }

    @Override
    protected void saveRate(Rate rate) {
        this.repository.save(rate);
    }

}
