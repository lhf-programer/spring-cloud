package com.lvhaifeng.cloud.auth.server.modules.client.biz;

import com.alibaba.fastjson.JSON;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.GatewayRoute;
import com.lvhaifeng.cloud.auth.server.modules.client.mapper.GatewayRouteMapper;
import com.lvhaifeng.cloud.common.biz.BusinessBiz;
import com.lvhaifeng.cloud.common.constant.RedisKeyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网关路由处理类
 *
 * @author haifeng.lv
 * @date 2019-08-03 13:57
 */
@Service
public class GatewayRouteBiz extends BusinessBiz<GatewayRouteMapper, GatewayRoute> {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void updateSelectiveById(GatewayRoute entity) {
        super.updateSelectiveById(entity);
        updateGatewayRoute();
    }

    @Override
    public void insertSelective(GatewayRoute entity) {
        super.insertSelective(entity);
        updateGatewayRoute();
    }

    @Override
    public void deleteById(Object id) {
        GatewayRoute gatewayRoute = this.selectById(id);
        gatewayRoute.setEnabled(false);
        this.updateSelectiveById(gatewayRoute);
    }

    @Override
    public void updateById(GatewayRoute entity) {
        super.updateById(entity);
        updateGatewayRoute();
    }

    /**
     * 更新redis中的路由信息
     * @author haifeng.lv
     * @date 2019-08-03 13:57
     */
    public void updateGatewayRoute() {
        List<GatewayRoute> gatewayRoutes = this.selectListAll();
        redisTemplate.opsForValue().set(RedisKeyConstants.ZUUL_ROUTE_KEY, JSON.toJSONString(gatewayRoutes));
    }
}
