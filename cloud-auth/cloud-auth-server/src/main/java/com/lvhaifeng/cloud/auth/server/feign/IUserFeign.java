package com.lvhaifeng.cloud.auth.server.feign;

import com.lvhaifeng.cloud.auth.server.configuration.FeignConfiguration;
import com.lvhaifeng.cloud.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;


/**
 * 用户请求
 * @author haifeng.lv
 * @date 2019-07-29 10:32
 */
@FeignClient(value = "cloud-admin", configuration = FeignConfiguration.class)
public interface IUserFeign {
    /**
     * 根据用户名获取用户信息
     * @author haifeng.lv
     * @date 2019-07-29 10:44
     */
    @PostMapping("/user/getUserInfoByUsername")
    Result<Map<String, String>> getUserInfoByUsername(@RequestParam("username") String username);
}
