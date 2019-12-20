package com.lvhaifeng.cloud.auth.server.modules.user.exception;
 
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
 
/**
 * @Description password 模式错误处理，自定义登录失败异常信息
 * @Author haifeng.lv
 * @Date 2019/12/20 9:52
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
    public CustomOauthException(String msg) {
        super(msg);
    }
}