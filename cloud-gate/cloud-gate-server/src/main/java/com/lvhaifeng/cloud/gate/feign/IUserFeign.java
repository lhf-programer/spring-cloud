

package com.lvhaifeng.cloud.gate.feign;

import com.lvhaifeng.cloud.api.vo.authority.PermissionInfo;
import com.lvhaifeng.cloud.gate.config.FeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * ${DESCRIPTION}
 *
 * @author haifeng.lv
 * @version 2018-06-21 8:11
 */
@FeignClient(value = "cloud-admin", configuration = FeignConfiguration.class)
public interface IUserFeign {
    /**
     * 获取用户的菜单和按钮权限
     *
     * @return
     */
    @RequestMapping(value = "/api/user/permissions", method = RequestMethod.GET)
    List<PermissionInfo> getPermissionByUsername();

    /**
     * 获取所有菜单和按钮权限
     *
     * @return
     */
    @RequestMapping(value = "/api/permissions", method = RequestMethod.GET)
    List<PermissionInfo> getAllPermissionInfo();
}
