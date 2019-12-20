package com.lvhaifeng.cloud.auth.server.modules.user.exception;
 
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description 自定义处理器
 * @Author haifeng.lv
 * @Date 2019/12/20 9:50
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Autowired
	private ObjectMapper objectMapper;
 
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		Map<String, Object> map = new HashMap<>();
		map.put("error", "403");
		map.put("message", accessDeniedException.getMessage());
		map.put("path", request.getServletPath());
		map.put("timestamp", LocalDateTime.now());
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().write(objectMapper.writeValueAsString(map));
	}
}