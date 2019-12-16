package com.lvhaifeng.cloud.gate.ratelimit.config.repository.springdata;

import com.lvhaifeng.cloud.gate.ratelimit.config.Rate;
import com.lvhaifeng.cloud.gate.ratelimit.config.repository.AbstractRateLimiter;
import lombok.RequiredArgsConstructor;

/**
 * @Description 在内存速率限制器配置为开发环境
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
