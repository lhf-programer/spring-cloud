package com.lvhaifeng.cloud.auth.server.modules.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.AuthClient;

import java.util.List;

/**
 * @Description: 授权客户端
 * @Author: haifeng.lv
 * @Date: 2019-12-05
 */
public interface IAuthClientService extends IService<AuthClient> {
    String getToken(String clientId, String secret) throws Exception;
    List<String> findAllowedClient(String clientId);
    void validate(String clientId, String secret) throws Exception;
}
