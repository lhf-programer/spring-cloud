package com.lvhaifeng.cloud.auth.server.runner;

import com.alibaba.fastjson.JSON;
import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.auth.server.constant.RedisKeyConstant;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.GatewayRoute;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IGatewayRouteService;
import com.lvhaifeng.cloud.common.constant.RedisKeyConstants;
import com.lvhaifeng.cloud.common.util.AesUtils;
import com.lvhaifeng.cloud.common.util.EncoderUtils;
import com.lvhaifeng.cloud.common.util.RsaKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Description runner 初始化
 * @Author haifeng.lv
 * @Date 2019/12/16 17:38
 */
@Configuration
@Slf4j
public class AuthServerRunner implements CommandLineRunner {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private KeyConfiguration keyConfiguration;
    @Autowired
    private IGatewayRouteService gatewayRouteService;

    /**
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    @Value("${redis.aec-key:abcdef0123456789}")
    private String sKey;
    @Value("${redis.aec-iv:0123456789abcdef}")
    private String ivParameter;

    @Override
    public void run(String... args) throws Exception {
        boolean flag = false;
        if (redisTemplate.hasKey(RedisKeyConstant.REDIS_USER_PRI_KEY) && redisTemplate.hasKey(RedisKeyConstant.REDIS_USER_PUB_KEY)) {
            try {
                keyConfiguration.setUserPriKey(EncoderUtils.toBytes(AesUtils.decrypt(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_USER_PRI_KEY), sKey, ivParameter)));
                keyConfiguration.setUserPubKey(EncoderUtils.toBytes(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_USER_PUB_KEY)));
            } catch (Exception e) {
                log.error("初始化用户公钥/密钥异常...", e);
                flag = true;
            }
        }
        if (flag) {
            Map<String, byte[]> keyMap = RsaKeyUtils.generateKey(keyConfiguration.getUserSecret());
            keyConfiguration.setUserPriKey(keyMap.get("pri"));
            keyConfiguration.setUserPubKey(keyMap.get("pub"));
            // 密钥加密
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_USER_PRI_KEY, AesUtils.encrypt(EncoderUtils.toHexString(keyMap.get("pri")), sKey, ivParameter));
            // 公钥不加密
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_USER_PUB_KEY, EncoderUtils.toHexString(keyMap.get("pub")));
        }
        log.info("完成用户公钥/密钥的初始化...");
        flag = false;
        if (redisTemplate.hasKey(RedisKeyConstant.REDIS_SERVICE_PRI_KEY) && redisTemplate.hasKey(RedisKeyConstant.REDIS_SERVICE_PUB_KEY)) {
            try {
                keyConfiguration.setServicePriKey(EncoderUtils.toBytes(AesUtils.decrypt(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_SERVICE_PRI_KEY), sKey, ivParameter)));
                keyConfiguration.setServicePubKey(EncoderUtils.toBytes(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_SERVICE_PUB_KEY).toString()));
            } catch (Exception e) {
                log.error("初始化服务公钥/密钥异常...", e);
                flag = true;
            }
        } else {
            flag = true;
        }
        if (flag) {
            Map<String, byte[]> keyMap = RsaKeyUtils.generateKey(keyConfiguration.getServiceSecret());
            keyConfiguration.setServicePriKey(keyMap.get("pri"));
            keyConfiguration.setServicePubKey(keyMap.get("pub"));
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_SERVICE_PRI_KEY, AesUtils.encrypt(EncoderUtils.toHexString(keyMap.get("pri")), sKey, ivParameter));
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_SERVICE_PUB_KEY, EncoderUtils.toHexString(keyMap.get("pub")));
        }
        log.info("完成服务公钥/密钥的初始化...");
        List<GatewayRoute> gatewayRoutes = gatewayRouteService.list();
        redisTemplate.opsForValue().set(RedisKeyConstants.ZUUL_ROUTE_KEY, JSON.toJSONString(gatewayRoutes));
        log.info("完成网关路由的更新...");
    }
}
