package com.lvhaifeng.cloud.gate.ratelimit.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 表示用户给定时间内的速率限制视图。
 * limit - 用户可以执行多少请求。映射到X-RateLimit-Limit
 * remaining - 当前窗口中还有多少请求。映射到X-RateLimit-Remaining头
 * reset - 比率受限制补充的时期。映射到X-RateLimit-Reset报头
 * @author haifeng.lv
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    @Id
    private String key;
    private Long remaining;
    private Long reset;
    private Date expiration;
}
