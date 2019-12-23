package com.lvhaifeng.cloud.auth.server.modules.client.controller;

import com.lvhaifeng.cloud.common.constant.RequestHeaderConstants;
import com.lvhaifeng.cloud.common.vo.Result;
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
@ApiIgnore
public class RevokeTokenEndpoint {

    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;

    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public Result<Boolean> revokeToken(String access_token) {
        Result<Boolean> result = new Result<>();

        // 截取 token
        String realToken = getRealToken(access_token);
        if (consumerTokenServices.revokeToken(realToken)) {
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
