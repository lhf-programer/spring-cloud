package com.lvhaifeng.cloud.auth.server.modules.user.exception;
 
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
 
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
 
/**
 * @Description password 模式错误处理，自定义登录失败异常信息
 * @Author haifeng.lv
 * @Date 2019/12/20 9:52
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
	private static final long serialVersionUID = 1478842053473472921L;
 
	public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }
    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        gen.writeStartObject();
        gen.writeStringField("error", String.valueOf(value.getHttpErrorCode()));
        gen.writeStringField("message", value.getMessage());
        gen.writeStringField("path", request.getServletPath());
        gen.writeStringField("timestamp", LocalDateTime.now().toString());
        if (value.getAdditionalInformation()!=null) {
        for (Map.Entry<String, String> entry :
            value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}