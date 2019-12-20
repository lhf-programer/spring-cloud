package com.lvhaifeng.cloud.auth.client.feign;

import com.lvhaifeng.cloud.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description 获取授权服务得公钥与私钥
 * @Author haifeng.lv
 * @Date 2019/12/16 17:31
 */
@FeignClient(value = "${auth.serviceId}")
public interface IAuthClientFeign {
    @RequestMapping(value = "/authClient/token", method = RequestMethod.POST)
    Result<String> getAccessToken(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);

    @RequestMapping(value = "/authClient/servicePubKey", method = RequestMethod.POST)
    Result<byte[]> getServicePublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);
}
