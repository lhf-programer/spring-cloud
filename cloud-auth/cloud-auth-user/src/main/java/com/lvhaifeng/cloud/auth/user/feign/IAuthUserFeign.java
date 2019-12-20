package com.lvhaifeng.cloud.auth.user.feign;

import com.lvhaifeng.cloud.auth.client.feign.IAuthClientFeign;
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
public interface IAuthUserFeign extends IAuthClientFeign {
    @RequestMapping(value = "/authClient/userPubKey", method = RequestMethod.POST)
    Result<byte[]> getUserPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);
}
