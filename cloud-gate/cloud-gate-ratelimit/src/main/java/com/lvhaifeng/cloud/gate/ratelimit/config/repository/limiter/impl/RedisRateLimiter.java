package com.lvhaifeng.cloud.gate.ratelimit.config.repository.limiter.impl;

import com.lvhaifeng.cloud.gate.ratelimit.config.properties.RateLimitProperties.Policy;
import com.lvhaifeng.cloud.gate.ratelimit.config.Rate;
import com.lvhaifeng.cloud.gate.ratelimit.config.repository.limiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @Description redis 限流器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:51
 */
@RequiredArgsConstructor
public class RedisRateLimiter implements RateLimiter {
    private final RedisTemplate template;

    @Override
    public Rate consume(final Policy policy, final String key) {
        // 限流数量
        final Long limit = policy.getLimit();
        // 刷新速率
        final Long refreshInterval = policy.getRefreshInterval();
        // 当前已有多少请求
        final Long current = this.template.boundValueOps(key).increment(1L);
        // 获取过期时间
        Long expire = this.template.getExpire(key);
        if (expire == null || expire == -1) {
            this.template.expire(key, refreshInterval, SECONDS);
            expire = refreshInterval;
        }
        return new Rate(key, Math.max(-1, limit - current), SECONDS.toMillis(expire), null);
    }
}
