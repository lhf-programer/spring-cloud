package com.lvhaifeng.cloud.auth.server.modules.client.controller;

import com.lvhaifeng.cloud.auth.server.modules.client.biz.ClientBiz;
import com.lvhaifeng.cloud.auth.server.modules.client.entity.Client;
import com.lvhaifeng.cloud.common.msg.ObjectRestResponse;
import com.lvhaifeng.cloud.common.rest.BaseController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author haifeng.lv
 * @version 2018/12/26.
 */
@RestController
@RequestMapping("service")
public class ServiceController extends BaseController<ClientBiz, Client, String> {

    /**
     * 修改客户端的serviceId
     * @author haifeng.lv
     * @date 2019-08-03 13:54
     */
    @RequestMapping(value = "/{id}/client", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@PathVariable String id, String clients) {
        baseBiz.modifyClientServices(id, clients);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/client", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<Client>> getUsers(@PathVariable String id) {
        ObjectRestResponse<List<Client>> entityObjectRestResponse = new ObjectRestResponse<>();
        Object o = baseBiz.getClientServices(id);
        entityObjectRestResponse.data((List<Client>) o);
        return entityObjectRestResponse;
    }
}
