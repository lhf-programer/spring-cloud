package com.lvhaifeng.cloud.auth.server.modules.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.GatewayRoute;
import com.lvhaifeng.cloud.auth.server.modules.client.mapper.GatewayRouteMapper;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IGatewayRouteService;
import com.lvhaifeng.cloud.common.constant.RedisKeyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Description: 路由
 * @Author: haifeng.lv
 * @Date:   2019-12-05
 */
@Service
public class GatewayRouteServiceImpl extends ServiceImpl<GatewayRouteMapper, GatewayRoute> implements IGatewayRouteService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private GatewayRouteMapper gatewayRouteMapper;

    /**
     * 更新redis中的路由信息
     * @author haifeng.lv
     * @date 2019-08-03 13:57
     */
    public void updateGatewayRoute() {
        List<GatewayRoute> gatewayRoutes = gatewayRouteMapper.selectList(null);
        redisTemplate.opsForValue().set(RedisKeyConstants.ZUUL_ROUTE_KEY, JSON.toJSONString(gatewayRoutes));
    }

}
