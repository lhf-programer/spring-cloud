package com.lvhaifeng.cloud.auth.server.modules.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lvhaifeng.cloud.auth.server.jwt.client.ClientTokenHelper;
import com.lvhaifeng.cloud.auth.server.jwt.user.UserTokenHelper;
import com.lvhaifeng.cloud.auth.server.modules.client.vo.ClientInfo;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.AuthClient;
import com.lvhaifeng.cloud.auth.server.modules.client.mapper.AuthClientMapper;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import com.lvhaifeng.cloud.common.exception.auth.ClientInvalidException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.util.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description: 授权客户端
 * @Author: haifeng.lv
 * @Date: 2019-12-05
 */
@Service
public class AuthClientServiceImpl extends ServiceImpl<AuthClientMapper, AuthClient> implements IAuthClientService {
    @Resource
    private AuthClientMapper authClientMapper;
    @Autowired
    private ClientTokenHelper clientTokenUtil;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserTokenHelper jwtTokenUtil;

    /**
     * @Description 获取服务鉴权token
     * @Author haifeng.lv
     * @param: clientId 客户端 id
     * @param: secret 客户端密钥
     * @Date 2019/12/19 18:05
     * @return: java.lang.String
     */
    @Override
    public String apply(String clientId, String secret) throws Exception {
        AuthClient authClient = getAuthClient(clientId, secret);
        return clientTokenUtil.generateToken(new ClientInfo(authClient.getCode(), authClient.getName(), authClient.getId()));
    }

    /**
     * @Description 获取服务授权的客户端列表
     * @Author haifeng.lv
     * @param: clientId 客户端 id
     * @Date 2019/12/19 18:04
     * @return: java.util.List<java.lang.String>
     */
    @Override
    public List<String> getAllowedClient(String clientId) {
        AuthClient authClient = getAuthClient(clientId);
        List<String> clients = authClientMapper.selectAllowedClient(authClient.getId() + "");
        if (clients == null) {
            new ArrayList<String>();
        }
        return clients;
    }

    /**
     * @Description 校验合法性
     * @Author haifeng.lv
     * @param: clientId 客户端 id
     * @param: secret 客户端密钥
     * @Date 2019/12/19 11:38
     */
    @Override
    public void validate(String clientId, String secret) {
        AuthClient authClient = getAuthClient(clientId);
        if (authClient == null || !authClient.getSecret().equals(secret)) {
            throw new ClientInvalidException("客户端没有找到或者客户端密钥错误！");
        }
    }

    /**
     * @description 验证
     * @author haifeng.lv
     * @param: token
     * @updateTime 2019/12/12 17:10
     * @return: java.lang.Boolean
     */
    @Override
    public Boolean invalid(String token) throws Exception {
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(token);
        redisTemplate.delete(RedisKeyUtils.buildUserAbleKey(infoFromToken.getId(), infoFromToken.getExpireTime()));
        redisTemplate.opsForValue().set(RedisKeyUtils.buildUserDisableKey(infoFromToken.getId(), infoFromToken.getExpireTime()), "1");
        return true;
    }

    /**
     * @description 获取授权服务
     * @author haifeng.lv
     * @param clientId 客户端 id
     * @param secret 密钥
     * @updateTime 2019/12/12 17:09
     * @return: com.lvhaifeng.cloud.auth.server.modules.client.entity.AuthClient
     */
    private AuthClient getAuthClient(String clientId, String secret) {
        AuthClient authClient = getAuthClient(clientId);
        if (authClient == null || !authClient.getSecret().equals(secret)) {
            throw new ClientInvalidException("客户端没有找到或者客户端密钥错误！");
        }
        return authClient;
    }

    /**
     * @Description 获取授权客户端
     * @Author haifeng.lv
     * @param: clientId 客户端 id
     * @Date 2019/12/19 11:36
     * @return: com.lvhaifeng.cloud.auth.server.modules.client.entity.AuthClient
     */
    private AuthClient getAuthClient(String clientId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", clientId);
        AuthClient authClient = authClientMapper.selectOne(queryWrapper);
        return authClient;
    }

}
