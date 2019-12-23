package com.lvhaifeng.cloud.gate.ratelimit.config.repository.limiter;

import com.lvhaifeng.cloud.gate.ratelimit.config.properties.RateLimitProperties.Policy;
import com.lvhaifeng.cloud.gate.ratelimit.config.Rate;

import java.util.Date;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Description 抽象限流器 (仅给内存限流器与jpa 数据库限流器)
 * @Author haifeng.lv
 * @Date 2019/12/18 14:23
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

    /**
     * @Description 创建速率
     * @Author haifeng.lv
     * @param: policy
     * @param: key
     * @Date 2019/12/18 14:19
     * @return: com.lvhaifeng.cloud.gate.ratelimit.config.Rate
     */
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
     * @Description 刷新速率
     * @Author haifeng.lv
     * @param: rate
     * @Date 2019/12/21 17:39
     */
    private void updateRate(final Rate rate) {
        if (rate.getReset() > 0) {
            Long reset = rate.getExpiration().getTime() - System.currentTimeMillis();
            rate.setReset(reset);
        }
        rate.setRemaining(Math.max(-1, rate.getRemaining() - 1));
    }

    /**
     * @Description 是否过期
     * @Author haifeng.lv
     * @param: rate
     * @Date 2019/12/21 17:40
     * @return: boolean
     */
    private boolean isExpired(final Rate rate) {
        return rate == null || (rate.getExpiration().getTime() < System.currentTimeMillis());
    }
}
