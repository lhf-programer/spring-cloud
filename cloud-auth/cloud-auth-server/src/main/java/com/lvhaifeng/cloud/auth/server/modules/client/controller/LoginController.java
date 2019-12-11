package com.lvhaifeng.cloud.auth.server.modules.client.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname LoginController
 * @Description 仅为 swagger使用
 * @Date 2019/12/7 15:41
 * @Author haifeng.lv
 */
@RestController
@Api(tags = "登录")
public class LoginController {

    @Value("${swagger.zuul.host}")
    private String host;

    @Value("${swagger.zuul.prefix}")
    private String prefix;

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping(value = "getToken", method = RequestMethod.POST)
    @ApiOperation(value="登录获取 token", notes="登录获取 token")
    public JSONObject getToken(@RequestParam(name="Authorization",required=true) String Authorization,
                           @RequestParam(name="grant_type", defaultValue="password") String grant_type,
                           @RequestParam(name="username",required=true) String username,
                           @RequestParam(name="password",required=true) String password,
                           HttpServletResponse httpServletResponse) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grant_type);
        params.add("username", username);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", Authorization);
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        String requestUrl = "http://" + host + prefix + "/oauth/token";
        ResponseEntity<JSONObject> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, JSONObject.class);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Cache-Control","no-cache");

        return response.getBody();
    }

}