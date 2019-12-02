package com.lvhaifeng.cloud.auth.server.modules.client.mapper;


import com.lvhaifeng.cloud.auth.server.modules.client.entity.ClientService;
import com.lvhaifeng.cloud.common.mapper.CommonMapper;

public interface ClientServiceMapper extends CommonMapper<ClientService> {
    void deleteByServiceId(String id);
}
