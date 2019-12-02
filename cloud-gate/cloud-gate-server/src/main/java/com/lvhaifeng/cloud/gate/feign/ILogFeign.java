

package com.lvhaifeng.cloud.gate.feign;

import com.lvhaifeng.cloud.api.vo.log.LogInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ${DESCRIPTION}
 *
 * @author haifeng.lv
 * @version 2018-07-01 15:16
 */
@FeignClient("cloud-admin")
public interface ILogFeign {
  @RequestMapping(value="/api/log/save",method = RequestMethod.POST)
  public void saveLog(LogInfo info);
}
