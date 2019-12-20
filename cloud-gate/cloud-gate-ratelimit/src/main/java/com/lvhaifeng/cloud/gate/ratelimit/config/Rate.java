package com.lvhaifeng.cloud.gate.ratelimit.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Description 表示用户给定时间内的速率限制视图。
 * key - jpa 限流器时使用
 * remaining - 当前窗口中还有多少请求。映射到X-RateLimit-Remaining头
 * reset - 比率受限制补充的时期。映射到X-RateLimit-Reset报头
 * expiration - 仅给内存限流器与jpa 数据库限流器
 * @Author haifeng.lv
 * @Date 2019/12/16 17:52
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
