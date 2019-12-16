package com.lvhaifeng.cloud.auth.server.feign;

import com.lvhaifeng.cloud.auth.server.configuration.FeignConfiguration;
import com.lvhaifeng.cloud.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;


/**
 * @Description 用户请求
 * @Author haifeng.lv
 * @Date 2019/12/16 17:34
 */
@FeignClient(value = "cloud-admin", configuration = FeignConfiguration.class)
public interface IUserFeign {
    /**
     * @Description 根据用户名获取用户信息
     * @Author haifeng.lv
     * @param: username 用户名
     * @Date 2019/12/16 17:34
     * @return: com.lvhaifeng.cloud.common.vo.Result<java.util.Map<java.lang.String,java.lang.String>>
     */
    @PostMapping("/user/getUserInfoByUsername")
    Result<Map<String, String>> getUserInfoByUsername(@RequestParam("username") String username);
}
