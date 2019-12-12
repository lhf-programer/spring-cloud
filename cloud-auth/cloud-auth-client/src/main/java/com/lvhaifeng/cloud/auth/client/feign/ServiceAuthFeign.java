package com.lvhaifeng.cloud.auth.client.feign;

import com.lvhaifeng.cloud.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description 获取授权服务得公钥与私钥
 * @author haifeng.lv
 * @updateTime 2019/12/12 16:58
 */
@FeignClient(value = "${auth.serviceId}")
public interface ServiceAuthFeign {
    @RequestMapping(value = "/authClient/allowedClient")
    Result<List<String>> getAllowedClient(@RequestParam("serviceId") String serviceId, @RequestParam("secret") String secret);

    @RequestMapping(value = "/authClient/token", method = RequestMethod.POST)
    Result<String> getAccessToken(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);

    @RequestMapping(value = "/authClient/servicePubKey", method = RequestMethod.POST)
    Result<byte[]> getServicePublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);

    @RequestMapping(value = "/authClient/userPubKey", method = RequestMethod.POST)
    Result<byte[]> getUserPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);
}
