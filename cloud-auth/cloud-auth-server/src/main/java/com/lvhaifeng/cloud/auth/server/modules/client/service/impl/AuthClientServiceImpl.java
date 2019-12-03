package com.lvhaifeng.cloud.auth.server.modules.client.service.impl;

import com.lvhaifeng.cloud.auth.server.jwt.client.ClientTokenUtil;
import com.lvhaifeng.cloud.auth.server.modules.client.bean.ClientInfo;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.Client;
import com.lvhaifeng.cloud.auth.server.modules.client.mapper.ClientMapper;
import com.lvhaifeng.cloud.auth.server.modules.client.service.AuthClientService;
import com.lvhaifeng.cloud.common.exception.auth.ClientInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haifeng.lv
 * @version 2018/9/10
 */
@Service
public class AuthClientServiceImpl implements AuthClientService {
    @Resource
    private ClientMapper clientMapper;
    @Autowired
    private ClientTokenUtil clientTokenUtil;

    @Override
    public String apply(String clientId, String secret) throws Exception {
        Client client = getClient(clientId, secret);
        return clientTokenUtil.generateToken(new ClientInfo(client.getCode(), client.getName(), client.getId()));
    }

    private Client getClient(String clientId, String secret) {
        Client client = new Client();
        client.setCode(clientId);
        client = clientMapper.selectOne(client);
        if (client == null || !client.getSecret().equals(secret)) {
            throw new ClientInvalidException("客户端没有找到或者客户端密钥错误！");
        }
        return client;
    }

    @Override
    public void validate(String clientId, String secret) {
        Client client = new Client();
        client.setCode(clientId);
        client = clientMapper.selectOne(client);
        if (client == null || !client.getSecret().equals(secret)) {
            throw new ClientInvalidException("客户端没有找到或者客户端密钥错误！");
        }
    }

    @Override
    public List<String> getAllowedClient(String clientId, String secret) {
        Client info = this.getClient(clientId, secret);
        List<String> clients = clientMapper.selectAllowedClient(info.getId() + "");
        if (clients == null) {
            new ArrayList<String>();
        }
        return clients;
    }

    @Override
    public List<String> getAllowedClient(String serviceId) {
        Client info = getClient(serviceId);
        List<String> clients = clientMapper.selectAllowedClient(info.getId() + "");
        if (clients == null) {
            new ArrayList<String>();
        }
        return clients;
    }

    private Client getClient(String clientId) {
        Client client = new Client();
        client.setCode(clientId);
        client = clientMapper.selectOne(client);
        return client;
    }
}
