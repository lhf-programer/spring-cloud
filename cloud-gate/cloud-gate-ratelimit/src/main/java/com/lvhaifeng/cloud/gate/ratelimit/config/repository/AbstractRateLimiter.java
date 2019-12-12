

package com.lvhaifeng.cloud.gate.ratelimit.config.repository;


import com.lvhaifeng.cloud.gate.ratelimit.config.properties.RateLimitProperties.Policy;
import com.lvhaifeng.cloud.gate.ratelimit.config.Rate;
import com.lvhaifeng.cloud.gate.ratelimit.config.RateLimiter;

import java.util.Date;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author haifeng.lv
 * @since 2018-08-28
 */
public abstract class AbstractRateLimiter implements RateLimiter {

    protected abstract Rate getRate(String key);
    protected abstract void saveRate(Rate rate);

    @Override
    public synchronized Rate consume(final Policy policy, final String key) {
        Rate rate = this.create(policy, key);
        this.updateRate(rate);
        this.saveRate(rate);
        return rate;
    }

    private Rate create(final Policy policy, final String key) {
        Rate rate = this.getRate(key);
        if (isExpired(rate)) {
            final Long limit = policy.getLimit();
            final Long refreshInterval = SECONDS.toMillis(policy.getRefreshInterval());
            final Date expiration = new Date(System.currentTimeMillis() + refreshInterval);

            rate = new Rate(key, limit, refreshInterval, expiration);
        }
        return rate;
    }

    /**
     * @description 刷新速率
     * @author haifeng.lv
     * @param: rate
     * @updateTime 2019/11/29 15:24
     */
    private void updateRate(final Rate rate) {
        if (rate.getReset() > 0) {
            Long reset = rate.getExpiration().getTime() - System.currentTimeMillis();
            rate.setReset(reset);
        }
        rate.setRemaining(Math.max(-1, rate.getRemaining() - 1));
    }

    /**
     * @description 是否超过存活时间
     * @author haifeng.lv
     * @param: rate
     * @updateTime 2019/11/29 15:25
     * @return: boolean
     */
    private boolean isExpired(final Rate rate) {
        return rate == null || (rate.getExpiration().getTime() < System.currentTimeMillis());
    }
}
