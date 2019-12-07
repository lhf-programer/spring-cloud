package com.lvhaifeng.cloud.auth.server.runner;

import com.alibaba.fastjson.JSON;
import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.auth.server.constant.RedisKeyConstant;
import com.lvhaifeng.cloud.auth.server.jwt.AECUtil;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.GatewayRoute;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IGatewayRouteService;
import com.lvhaifeng.cloud.common.constant.RedisKeyConstants;
import com.lvhaifeng.cloud.common.util.RsaKeyHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author haifeng.lv
 * @version 2018/12/17.
 */
@Configuration
@Slf4j
public class AuthServerRunner implements CommandLineRunner {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KeyConfiguration keyConfiguration;

    @Autowired
    private AECUtil aecUtil;

    @Autowired
    private RsaKeyHelper rsaKeyHelper;

    @Autowired
    private IGatewayRouteService gatewayRouteService;

    @Override
    public void run(String... args) throws Exception {
        boolean flag = false;
        if (redisTemplate.hasKey(RedisKeyConstant.REDIS_USER_PRI_KEY) && redisTemplate.hasKey(RedisKeyConstant.REDIS_USER_PUB_KEY)) {
            try {
                keyConfiguration.setUserPriKey(rsaKeyHelper.toBytes(aecUtil.decrypt(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_USER_PRI_KEY))));
                keyConfiguration.setUserPubKey(rsaKeyHelper.toBytes(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_USER_PUB_KEY)));
            } catch (Exception e) {
                log.error("初始化用户公钥/密钥异常...", e);
                flag = true;
            }
        }
        if (flag) {
            Map<String, byte[]> keyMap = rsaKeyHelper.generateKey(keyConfiguration.getUserSecret());
            keyConfiguration.setUserPriKey(keyMap.get("pri"));
            keyConfiguration.setUserPubKey(keyMap.get("pub"));
            // 密钥加密
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_USER_PRI_KEY, aecUtil.encrypt(rsaKeyHelper.toHexString(keyMap.get("pri"))));
            // 公钥不加密
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_USER_PUB_KEY, rsaKeyHelper.toHexString(keyMap.get("pub")));
        }
        log.info("完成用户公钥/密钥的初始化...");
        flag = false;
        if (redisTemplate.hasKey(RedisKeyConstant.REDIS_SERVICE_PRI_KEY) && redisTemplate.hasKey(RedisKeyConstant.REDIS_SERVICE_PUB_KEY)) {
            try {
                keyConfiguration.setServicePriKey(rsaKeyHelper.toBytes(aecUtil.decrypt(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_SERVICE_PRI_KEY).toString())));
                keyConfiguration.setServicePubKey(rsaKeyHelper.toBytes(redisTemplate.opsForValue().get(RedisKeyConstant.REDIS_SERVICE_PUB_KEY).toString()));
            } catch (Exception e) {
                log.error("初始化服务公钥/密钥异常...", e);
                flag = true;
            }
        } else {
            flag = true;
        }
        if (flag) {
            Map<String, byte[]> keyMap = rsaKeyHelper.generateKey(keyConfiguration.getServiceSecret());
            keyConfiguration.setServicePriKey(keyMap.get("pri"));
            keyConfiguration.setServicePubKey(keyMap.get("pub"));
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_SERVICE_PRI_KEY, aecUtil.encrypt(rsaKeyHelper.toHexString(keyMap.get("pri"))));
            redisTemplate.opsForValue().set(RedisKeyConstant.REDIS_SERVICE_PUB_KEY, rsaKeyHelper.toHexString(keyMap.get("pub")));
        }
        log.info("完成服务公钥/密钥的初始化...");
        List<GatewayRoute> gatewayRoutes = gatewayRouteService.list();
        redisTemplate.opsForValue().set(RedisKeyConstants.ZUUL_ROUTE_KEY, JSON.toJSONString(gatewayRoutes));
        log.info("完成网关路由的更新...");
    }
}
