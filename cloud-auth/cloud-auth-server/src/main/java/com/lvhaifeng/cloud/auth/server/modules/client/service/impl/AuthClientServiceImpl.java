package com.lvhaifeng.cloud.auth.server.modules.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lvhaifeng.cloud.auth.server.feign.IUserFeign;
import com.lvhaifeng.cloud.auth.server.jwt.client.ClientTokenUtil;
import com.lvhaifeng.cloud.auth.server.jwt.user.JwtTokenUtil;
import com.lvhaifeng.cloud.auth.server.modules.client.bean.ClientInfo;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.AuthClient;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.AuthClientService;
import com.lvhaifeng.cloud.auth.server.modules.client.mapper.AuthClientMapper;
import com.lvhaifeng.cloud.auth.server.modules.client.mapper.AuthClientServiceMapper;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import com.lvhaifeng.cloud.common.exception.auth.ClientInvalidException;
import com.lvhaifeng.cloud.common.exception.auth.UserInvalidException;
import com.lvhaifeng.cloud.common.jwt.IJWTInfo;
import com.lvhaifeng.cloud.common.jwt.JWTInfo;
import com.lvhaifeng.cloud.common.util.RedisKeyUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;

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
    private ClientTokenUtil clientTokenUtil;
    @Resource
    private AuthClientServiceMapper authClientServiceMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    /**
     * 获取服务鉴权token
     * @param clientId 客户端 id
     * @param secret 密钥
     * @throws Exception
     */
    @Override
    public String apply(String clientId, String secret) throws Exception {
        AuthClient authClient = getAuthClient(clientId, secret);
        return clientTokenUtil.generateToken(new ClientInfo(authClient.getCode(), authClient.getName(), authClient.getId()));
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
     * 校验合法性
     * @param clientId 客户端 id
     * @param secret 密钥
     * @throws Exception
     */
    @Override
    public void validate(String clientId, String secret) {
        AuthClient authClient = getAuthClient(clientId);
        if (authClient == null || !authClient.getSecret().equals(secret)) {
            throw new ClientInvalidException("客户端没有找到或者客户端密钥错误！");
        }
    }

    /**
     * 获取授权的客户端列表
     * @param clientId 客户端 id
     * @param secret 密钥
     * @return
     */
    @Override
    public List<String> getAllowedClient(String clientId, String secret) {
        AuthClient authClient = this.getAuthClient(clientId, secret);
        List<String> clients = authClientMapper.selectAllowedClient(authClient.getId() + "");
        if (clients == null) {
            new ArrayList<String>();
        }
        return clients;
    }

    /**
     * 获取服务授权的客户端列表
     * @param clientId 客户端 id
     * @return
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

    private AuthClient getAuthClient(String clientId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", clientId);
        AuthClient authClient = authClientMapper.selectOne(queryWrapper);
        return authClient;
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
        redisTemplate.delete(RedisKeyUtil.buildUserAbleKey(infoFromToken.getId(), infoFromToken.getExpireTime()));
        redisTemplate.opsForValue().set(RedisKeyUtil.buildUserDisableKey(infoFromToken.getId(), infoFromToken.getExpireTime()), "1");
        return true;
    }

    /**
     * 根据客户端id获取所有可以访问的服务
     * @author haifeng.lv
     * @date 2019-08-03 13:55
     */
    @Override
    public List<AuthClient> getClientServices(String id) {
        return authClientMapper.selectAuthorityServiceInfo(id);
    }

    /**
     * 更新服务可以访问的服务
     * @author haifeng.lv
     * @date 2019-08-03 13:56
     */
    @Override
    public void modifyClientServices(String id, String clients) {
        authClientServiceMapper.deleteByServiceId(id);
        if (!StringUtils.isEmpty(clients)) {
            String[] serverIds = clients.split(",");
            for (String serverId : serverIds) {
                AuthClientService authClientService = new AuthClientService();
                authClientService.setServiceId(serverId);
                authClientService.setClientId(id + "");
                authClientServiceMapper.insert(authClientService);
            }
        }
    }

}
