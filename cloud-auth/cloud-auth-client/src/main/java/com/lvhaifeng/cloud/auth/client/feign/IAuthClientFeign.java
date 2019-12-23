package com.lvhaifeng.cloud.auth.client.feign;

import com.lvhaifeng.cloud.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description 获取授权服务得公钥与私钥
 * @Author haifeng.lv
 * @Date 2019/12/16 17:31
 */
@FeignClient(value = "${auth.serviceId}")
public interface IAuthClientFeign {
    /**
     * @Description 获取 token
     * @Author haifeng.lv
     * @param: clientId 客户端 id
     * @param: secret 客户端密钥
     * @Date 2019/12/20 17:46
     * @return: com.lvhaifeng.cloud.common.vo.Result<java.lang.String>
     */
    @RequestMapping(value = "/authClient/getToken", method = RequestMethod.POST)
    Result<String> getToken(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);
    /**
     * @Description 获取 token
     * @Author haifeng.lv
     * @param: clientId 客户端 id
     * @param: secret 客户端密钥
     * @Date 2019/12/20 17:47
     * @return: com.lvhaifeng.cloud.common.vo.Result<byte[]>
     */
    @RequestMapping(value = "/authClient/clientPubKey", method = RequestMethod.POST)
    Result<byte[]> getClientPubKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);
    /**
     * @Description 获取可访问客户端列表
     * @Author haifeng.lv
     * @param: clientId 客户端 id
     * @Date 2019/12/20 17:58
     * @return: com.lvhaifeng.cloud.common.vo.Result<byte[]>
     */
    @RequestMapping(value = "/authClient/getAllowedClient", method = RequestMethod.POST)
    Result<List<String>> getAllowedClient(@RequestParam("clientId") String clientId);
}
