package com.lvhaifeng.cloud.auth.server.modules.client.controller;

import com.lvhaifeng.cloud.auth.server.modules.client.biz.GatewayRouteBiz;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.GatewayRoute;
import com.lvhaifeng.cloud.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gatewayRoute")
public class GatewayRouteController extends BaseController<GatewayRouteBiz, GatewayRoute,String> {

}