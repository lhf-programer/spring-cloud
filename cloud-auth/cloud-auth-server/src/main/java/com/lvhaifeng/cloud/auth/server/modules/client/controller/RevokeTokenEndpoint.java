package com.lvhaifeng.cloud.auth.server.modules.client.controller;

import com.lvhaifeng.cloud.auth.server.modules.client.service.IAuthClientService;
import com.lvhaifeng.cloud.common.constant.RequestHeaderConstants;
import com.lvhaifeng.cloud.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Description 客户端退出登录
 * @Author haifeng.lv
 * @Date 2019/12/16 17:36
 */
@FrameworkEndpoint
@Api(tags="客户端退出登录")
public class RevokeTokenEndpoint {

    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;
    @Autowired
    private IAuthClientService authClientService;

    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    @ApiOperation(value="退出登录", notes="退出登录")
    public Result<Boolean> revokeToken(String access_token) throws Exception {
        Result<Boolean> result = new Result<>();

        // 截取 token
        String realToken = getRealToken(access_token);
        if (consumerTokenServices.revokeToken(realToken)) {
            authClientService.invalid(realToken);
            result.setResult(true);
            return result;
        } else {
            result.setResult(false);
            return result;
        }
    }

    private String getRealToken(String originToken) {
        if (originToken != null && originToken.startsWith(RequestHeaderConstants.JWT_TOKEN_TYPE)) {
            originToken = originToken.substring(RequestHeaderConstants.JWT_TOKEN_TYPE.length());
        }
        return originToken;
    }
}
