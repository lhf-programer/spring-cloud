package com.lvhaifeng.cloud.auth.server.modules.client.biz;

import com.lvhaifeng.cloud.auth.server.modules.client.entity.Client;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.ClientService;
import com.lvhaifeng.cloud.auth.server.modules.client.mapper.ClientMapper;
import com.lvhaifeng.cloud.auth.server.modules.client.mapper.ClientServiceMapper;
import com.lvhaifeng.cloud.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClientBiz extends BaseBiz<ClientMapper, Client> {
    @Resource
    private ClientServiceMapper clientServiceMapper;
    @Autowired
    private ClientServiceBiz clientServiceBiz;

    /**
     * 根据客户端id获取所有可以访问的服务
     *
     * @author haifeng.lv
     * @date 2019-08-03 13:55
     */
    public List<Client> getClientServices(String id) {
        return mapper.selectAuthorityServiceInfo(id);
    }

    /**
     * 更新服务可以访问的服务
     *
     * @author haifeng.lv
     * @date 2019-08-03 13:56
     */
    public void modifyClientServices(String id, String clients) {
        clientServiceMapper.deleteByServiceId(id);
        if (!StringUtils.isEmpty(clients)) {
            String[] mem = clients.split(",");
            for (String m : mem) {
                ClientService clientService = new ClientService();
                clientService.setServiceId(m);
                clientService.setClientId(id + "");
                clientServiceBiz.insertSelective(clientService);
            }
        }
    }
}
