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
 * @Date:   2019-12-05
 * @Version: V1.0
 */
@Service
public class AuthClientServiceImpl extends ServiceImpl<AuthClientMapper, AuthClient> implements IAuthClientService {
    @Resource
    private AuthClientMapper authClientMapper;
    @Autowired
    private ClientTokenUtil clientTokenUtil;
    private JwtTokenUtil jwtTokenUtil;
    private IUserFeign iUserFeign;
    @Resource
    private AuthClientServiceMapper authClientServiceMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取服务鉴权token
     * @param clientId
     * @param secret
     * @return
     * @throws Exception
     */
    @Override
    public String apply(String clientId, String secret) throws Exception {
        AuthClient authClient = getAuthClient(clientId, secret);
        return clientTokenUtil.generateToken(new ClientInfo(authClient.getCode(), authClient.getName(), authClient.getId()));
    }

    private AuthClient getAuthClient(String clientId, String secret) {
        AuthClient authClient = getAuthClient(clientId);
        if (authClient == null || !authClient.getSecret().equals(secret)) {
            throw new ClientInvalidException("客户端没有找到或者客户端密钥错误！");
        }
        return authClient;
    }

    /**
     * 校验合法性
     *
     * @param clientId
     * @param secret
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
     *
     * @param clientId
     * @param secret
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
     * @param clientId
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

    @Override
    public Boolean invalid(String token) throws Exception {
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(token);
        redisTemplate.delete(RedisKeyUtil.buildUserAbleKey(infoFromToken.getId(), infoFromToken.getExpireTime()));
        redisTemplate.opsForValue().set(RedisKeyUtil.buildUserDisableKey(infoFromToken.getId(), infoFromToken.getExpireTime()), "1");
        return true;
    }

    @Override
    public String login(String username, String password) throws Exception {
        Map<String, String> data = iUserFeign.validate(username, password).getResult();
        String token;
        if (!StringUtils.isEmpty(data.get("id"))) {
            Map<String, String> map = new HashMap<>();
            JWTInfo jwtInfo = new JWTInfo(data.get("username"), data.get("id"), data.get("name"));
            Date expireTime = DateTime.now().plusSeconds(jwtTokenUtil.getExpire()).toDate();
            token = jwtTokenUtil.generateToken(jwtInfo, map, expireTime);
            redisTemplate.opsForValue().set(RedisKeyUtil.buildUserAbleKey(data.get("id"), expireTime), "1");
            return token;
        }
        throw new UserInvalidException("用户不存在或账户密码错误!");
    }

    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);
    }

    @Override
    public String refresh(String oldToken) throws Exception {
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(oldToken);
        invalid(oldToken);
        Date expireTime = DateTime.now().plusSeconds(jwtTokenUtil.getExpire()).toDate();
        redisTemplate.opsForValue().set(RedisKeyUtil.buildUserAbleKey(infoFromToken.getId(), expireTime), "1");
        return jwtTokenUtil.generateToken(infoFromToken, infoFromToken.getOtherInfo(), expireTime);
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
