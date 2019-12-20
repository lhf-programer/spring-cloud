package com.lvhaifeng.cloud.auth.server.modules.user.exception;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
 
/**
 * @Description 自定义 AuthExceptionEntryPoint用于tokan校验失败返回信息
 * @Author haifeng.lv
 * @Date 2019/12/20 9:47
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
 	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws ServletException {
 
		Map<String, Object> map = new HashMap<>();
		//401 未授权
		map.put("error", "401");
		map.put("message", authException.getMessage());
		map.put("path", request.getServletPath());
		map.put("timestamp", LocalDateTime.now());
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), map);
		} catch (Exception e) {
			throw new ServletException();
		}
	}
}