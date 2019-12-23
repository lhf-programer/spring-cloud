package com.lvhaifeng.cloud.auth.server.auth.user;

import com.lvhaifeng.cloud.auth.server.configuration.KeyConfiguration;
import com.lvhaifeng.cloud.common.auth.AuthHelper;
import com.lvhaifeng.cloud.common.vo.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 用户 token
 * @Author haifeng.lv
 * @Date 2019/12/19 10:41
 */
@Component
public class UserTokenHelper {
    @Autowired
    private KeyConfiguration keyConfiguration;
    /**
     * @Description 获取用户 token
     * @Author haifeng.lv
     * @param: token
     * @Date 2019/12/21 16:31
     * @return: com.lvhaifeng.cloud.common.vo.AuthInfo
     */
    public AuthInfo getInfoFromToken(String token) throws Exception {
        AuthInfo authInfo = AuthHelper.getInfoFromToken(token, keyConfiguration.getUserPubKey());
        return authInfo;
    }
}
