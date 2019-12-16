package com.lvhaifeng.cloud.auth.server.modules.client.controller;

import java.util.List;

import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.AuthClient;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import com.lvhaifeng.cloud.common.vo.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Description: 授权客户端
 * @Author: haifeng.lv
 * @Date: 2019-12-05
 */
@Slf4j
@RestController
@ApiIgnore
@RequestMapping("/authClient")
public class AuthClientController {
	@Autowired
	private IAuthClientService authClientService;
	@Autowired
    private KeyConfiguration keyConfiguration;

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Result<String> getAccessToken(String clientId, String secret) throws Exception {
        Result<String> result = new Result();
        result.setResult(authClientService.apply(clientId, secret));
        return result;
    }

    @RequestMapping(value = "/allowedClient")
    public Result<List<String>> getAllowedClient(String serviceId, String secret) {
        Result<List<String>> result = new Result();
        result.setResult(authClientService.getAllowedClient(serviceId, secret));
        return result;
    }

    @RequestMapping(value = "/servicePubKey",method = RequestMethod.POST)
    public Result<byte[]> getServicePublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret) throws Exception {
        authClientService.validate(clientId, secret);
        Result<byte[]> result = new Result();
        result.setResult(keyConfiguration.getServicePubKey());
        return result;
    }

    @RequestMapping(value = "/userPubKey",method = RequestMethod.POST)
    public Result<byte[]> getUserPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret) throws Exception {
        authClientService.validate(clientId, secret);
        Result<byte[]> result = new Result();
        result.setResult(keyConfiguration.getUserPubKey());
        return result;
    }

     @RequestMapping(value = "/{id}/client", method = RequestMethod.PUT)
     @ResponseBody
     public Result modifyUsers(@PathVariable String id, String clients) {
         authClientService.modifyClientServices(id, clients);
         return new Result();
     }

     @RequestMapping(value = "/{id}/client", method = RequestMethod.GET)
     @ResponseBody
     public Result<List<AuthClient>> getUsers(@PathVariable String id) {
         Result<List<AuthClient>> result = new Result<>();
         List<AuthClient> clientServices = authClientService.getClientServices(id);
         result.setResult(clientServices);
         return result;
     }

}
