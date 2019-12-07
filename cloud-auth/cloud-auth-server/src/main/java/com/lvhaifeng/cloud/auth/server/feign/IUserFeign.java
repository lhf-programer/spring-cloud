package com.lvhaifeng.cloud.auth.server.feign;

import com.lvhaifeng.cloud.auth.server.configuration.FeignConfiguration;
import com.lvhaifeng.cloud.common.vo.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;


/**
 * @author haifeng.lv
 * @date 2019-07-29 10:32
 */
@FeignClient(value = "cloud-admin", configuration = FeignConfiguration.class)
public interface IUserFeign {
    /**
     * 校验用户名和密码是否正确
     *
     * @author haifeng.lv
     * @date 2019-07-29 10:44
     */
    @PostMapping("/user/validate")
    Result<Map<String, String>> validate(@RequestParam("username") String username, @RequestParam("password") String password);

    /**
     * 根据用户名获取用户信息
     *
     * @author haifeng.lv
     * @date 2019-07-29 10:44
     */
    @PostMapping("/user/getUserInfoByUsername")
    Result<Map<String, String>> getUserInfoByUsername(@RequestParam("username") String username);

}
