package com.lvhaifeng.cloud.gate.ratelimit.config.repository;

import com.lvhaifeng.cloud.gate.ratelimit.config.Rate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 在内存速率限制器配置为开发环境
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
