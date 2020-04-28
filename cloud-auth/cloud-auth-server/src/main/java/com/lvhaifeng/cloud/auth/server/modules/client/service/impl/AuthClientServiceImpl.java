package com.lvhaifeng.cloud.auth.server.modules.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lvhaifeng.cloud.auth.server.auth.client.ClientTokenHelper;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.AuthClient;
import com.lvhaifeng.cloud.auth.server.modules.client.mapper.AuthClientMapper;
import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import com.lvhaifeng.cloud.common.exception.auth.ClientInvalidException;
import com.lvhaifeng.cloud.common.vo.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ClientTokenHelper clientTokenHelper;

    /**
     * @Description 获取服务鉴权token
     * @Author haifeng.lv
     * @param: clientId 客户端 id
     * @param: secret 客户端密钥
     * @Date 2019/12/19 18:05
     * @return: java.lang.String
     */
    @Override
    public String getToken(String clientId, String secret) throws Exception {
        AuthClient authClient = getAuthClient(clientId, secret);
        return clientTokenHelper.generateToken(new AuthInfo(authClient.getId(), authClient.getName()));
    }

    /**
     * @Description 获取服务授权的客户端列表
     * @Author haifeng.lv
     * @param: clientId 客户端 id
     * @Date 2019/12/19 18:04
     * @return: java.util.List<java.lang.String>
     */
    @Override
    public List<String> findAllowedClient(String clientId) {
        AuthClient authClient = getAuthClient(clientId);
        List<String> clients = authClientMapper.selectAllowedClient(authClient.getId());
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
