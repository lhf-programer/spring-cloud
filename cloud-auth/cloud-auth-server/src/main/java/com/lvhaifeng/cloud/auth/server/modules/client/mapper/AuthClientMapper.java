package com.lvhaifeng.cloud.auth.server.modules.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.AuthClient;

import java.util.List;

/**
 * @Description: 授权客户端
 * @Author: haifeng.lv
 * @Date: 2019-12-05
 */
public interface AuthClientMapper extends BaseMapper<AuthClient> {
    List<String> selectAllowedClient(String serviceId);
}
