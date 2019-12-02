

package com.lvhaifeng.cloud.auth.server.modules.client.service;


import java.util.List;

/**
 * @author haifeng.lv
 * @version 2018/9/10
 */
public interface AuthClientService {
    /**
     * 获取服务鉴权token
     *
     * @param clientId
     * @param secret
     * @return
     * @throws Exception
     */
    String apply(String clientId, String secret) throws Exception;

    /**
     * 获取授权的客户端列表
     *
     * @param serviceId
     * @param secret
     * @return
     */
    List<String> getAllowedClient(String serviceId, String secret);

    /**
     * 获取服务授权的客户端列表
     *
     * @param serviceId
     * @return
     */
    List<String> getAllowedClient(String serviceId);

    /**
     * 校验合法性
     *
     * @param clientId
     * @param secret
     * @throws Exception
     */
    void validate(String clientId, String secret) throws Exception;
}
