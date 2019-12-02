package com.lvhaifeng.cloud.auth.server.modules.client.mapper;


import com.lvhaifeng.cloud.auth.server.modules.client.entity.Client;
import com.lvhaifeng.cloud.common.mapper.CommonMapper;

import java.util.List;

public interface ClientMapper extends CommonMapper<Client> {

    List<String> selectAllowedClient(String serviceId);

    List<Client> selectAuthorityServiceInfo(String clientId);
}
