package com.lvhaifeng.cloud.auth.server.modules.client.controller;

import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import com.lvhaifeng.cloud.common.vo.Result;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

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

	/**
	 * @Description 获取 token
	 * @Author haifeng.lv
	 * @param: clientId 客户端 id
	 * @param: secret 客户端密钥
	 * @Date 2019/12/20 16:29
	 * @return: com.lvhaifeng.cloud.common.vo.Result<java.lang.String>
	 */
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public Result<String> getToken(String clientId, String secret) throws Exception {
        Result<String> result = new Result();
        result.setResult(authClientService.getToken(clientId, secret));
        return result;
    }

    @RequestMapping(value = "/clientPubKey",method = RequestMethod.POST)
    public Result<byte[]> getClientPubKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret) throws Exception {
        authClientService.validate(clientId, secret);
        Result<byte[]> result = new Result();
        result.setResult(keyConfiguration.getClientPubKey());
        return result;
    }

    @RequestMapping(value = "/userPubKey",method = RequestMethod.POST)
    public Result<byte[]> getUserPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret) throws Exception {
        authClientService.validate(clientId, secret);
        Result<byte[]> result = new Result();
        result.setResult(keyConfiguration.getUserPubKey());
        return result;
    }

    /**
     * @Description 获取可访问客户端
     * @Author haifeng.lv
     * @param: clientId 客户端 id
     * @Date 2019/12/20 18:00
     * @return: com.lvhaifeng.cloud.common.vo.Result<java.util.List<java.lang.String>>
     */
    @RequestMapping(value = "/getAllowedClient",method = RequestMethod.POST)
    public Result<List<String>> getAllowedClient(@RequestParam("clientId") String clientId) {
        Result<List<String>> result = new Result();
        List<String> allowedClient = authClientService.findAllowedClient(clientId);
        result.setResult(allowedClient);
        return result;
    }

}
