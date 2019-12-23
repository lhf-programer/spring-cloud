package com.lvhaifeng.cloud.auth.server.modules.user.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
 
/**
 * @Description password 模式错误处理，自定义登录失败异常信息
 * @Author haifeng.lv
 * @Date 2019/12/20 9:51
 */
@Component
public class ResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity
                .status(oAuth2Exception.getHttpErrorCode())
                .body(new OauthException(oAuth2Exception.getMessage()));
    }
}