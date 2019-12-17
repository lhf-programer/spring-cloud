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
 * @Description 仅为 swagger使用
 * @Author haifeng.lv
 * @Date 2019/12/16 17:35
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

    @RequestMapping(value = "token", method = RequestMethod.POST)
    @ApiOperation(value="登录获取 token", notes="登录获取 token")
    public JSONObject token(@RequestParam(name="Authorization",required=true) String Authorization,
                           @RequestParam(name="grant_type", defaultValue="password") String grant_type,
                           @RequestParam(name="username",required=true) String username,
                           @RequestParam(name="password",required=true) String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grant_type);
        params.add("username", username);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", Authorization);
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        String requestUrl = "http://" + host + prefix + "/oauth/token";
        ResponseEntity<JSONObject> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, JSONObject.class);

        return response.getBody();
    }

    @RequestMapping(value = "token", method = RequestMethod.DELETE)
    @ApiOperation(value="退出登录", notes="退出登录")
    public JSONObject token(@RequestParam(name="Authorization",required=true) String Authorization,
                           @RequestParam(name="access_token") String access_token) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", Authorization);
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        String requestUrl = "http://" + host + prefix + "/oauth/token?access_token=" + access_token;
        ResponseEntity<JSONObject> response = restTemplate.exchange(requestUrl, HttpMethod.DELETE, requestEntity, JSONObject.class);

        return response.getBody();
    }

}
