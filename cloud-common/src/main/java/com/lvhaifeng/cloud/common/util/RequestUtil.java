package com.lvhaifeng.cloud.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description 请求工具类
 * @Author haifeng.lv
 * @Date 2019/12/16 17:47
 */
@Slf4j
public class RequestUtil {

    public static HttpServletRequest getRequest() {
        RequestAttributes reqAttr = RequestContextHolder.getRequestAttributes();
        if (reqAttr != null && reqAttr instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) reqAttr).getRequest();
        }

        return null;
    }

    public static HttpServletResponse getResponse() {
        RequestAttributes reqAttr = RequestContextHolder.getRequestAttributes();
        if (reqAttr != null && reqAttr instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) reqAttr).getResponse();
        }

        return null;
    }

    public static boolean isRpcCall(HttpServletRequest request) {
        if (request == null) {
            return false;
        }

        // 如果是hessian请求则认为是rpc调用
        if (isHessianRequest(request)) {
            return true;
        }

        // 如果是soap的web service请求则认为是rpc调用
        if (isSoapRequest(request)) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否为soap调用
     *
     * @param request
     * @return
     */
    private static boolean isSoapRequest(HttpServletRequest request) {
        if (request == null) {
            return false;
        }

        String contentType = request.getContentType();
        if (contentType == null) {
            return false;
        }

        // sopa1.2协议中，ContentType为application/soap+xml
        if (contentType.contains("soap")) {
            return true;
        }

        // sopa协议中有一个请求头SOAPAction的项，该项可能为空串 因此只判断是否为null
        if (request.getHeader("SOAPAction") != null) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否为hessian调用
     *
     * @param request
     * @return
     */
    private static boolean isHessianRequest(HttpServletRequest request) {
        if (request == null) {
            return false;
        }

        String contentType = request.getContentType();
        if (contentType == null) {
            return false;
        }

        // Hessian协议的ContentType为x-application/hessian
        if (contentType.contains("hessian")) {
            return true;
        }

        return false;
    }

    private static String getFirstValidIp(String ipList) {
        if (ipList == null || ipList.length() == 0) {
            return null;
        }

        String[] ips = ipList.split(",");
        for (String ip : ips) {
            if (StringUtils.isBlank(ip)) {
                continue;
            }

            if ("unknown".equalsIgnoreCase(ip)) {
                continue;
            }

            return ip;
        }

        return null;
    }

    public static String getRequestIp() {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request == null) {
            return null;
        }

        String ip = getFirstValidIp(request.getHeader("X-Forwarded-For"));

        if (ip == null) {
            ip = getFirstValidIp(request.getHeader("X-FORWARDED-FOR"));
        } else {
            return ip;
        }

        if (ip == null) {
            ip = getFirstValidIp(request.getHeader("Proxy-Client-IP"));
        } else {
            return ip;
        }

        if (ip == null) {
            ip = getFirstValidIp(request.getHeader("WL-Proxy-Client-IP"));
        } else {
            return ip;
        }

        if (ip == null) {
            ip = getFirstValidIp(request.getHeader("HTTP_CLIENT_IP"));
        } else {
            return ip;
        }

        if (ip == null) {
            ip = getFirstValidIp(request.getHeader("HTTP_X_FORWARDED_FOR"));
        } else {
            return ip;
        }

        if (ip == null) {
            ip = request.getRemoteAddr();
        } else {
            return ip;
        }

        if (ip != null) {
            return ip.trim();
        }
        else {
            return null;
        }
    }

    /**
     * 获取原始的http请求的内容，主要用于获取web接口中请求内容
     *
     * @param request
     * @return
     */
    public static String getRequestString(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        // 如果是rpc调用，则不获取请求内容，rpc调用请求的内容是特定格式
        if (RequestUtil.isRpcCall(request)) {
            return null;
        }

        // 是GET方法则从query string中获取
        String method = request.getMethod();
        if (method == null) {
            method = StrUtils.EMPTY;
        }

        if (method.equalsIgnoreCase("GET")) {
            return request.getQueryString();
        }

        /**
         * 如果是post方法则从请求的body中获取,但需要区分文件上传的 情况
         */
        if (method.equalsIgnoreCase("POST")) {
            try {
                ServletInputStream inputStream = request.getInputStream();
                int length = request.getContentLength();
                if (length <= 0) {
                    return null;
                }

                byte[] bytes = new byte[length];
                int readSize = inputStream.read(bytes);
                if (readSize > 0) {
                    return new String(bytes, 0, readSize);
                }
                else {
                    return StrUtils.EMPTY;
                }
            } catch (Throwable t) {
                log.error("get post data body from request input stream fail", t);
            }
        }

        return null;
    }

    public static boolean isMultipart(HttpServletRequest request) {
        if (!isHttpPost(request)) {
            return false;
        }

        String contentType = request.getContentType();
        return (contentType != null && contentType.toLowerCase().startsWith("multipart/"));
    }

    public static boolean isHttpPost(HttpServletRequest request) {
        if (request == null) {
            return false;
        }

        String method = request.getMethod();
        if (method == null || !"post".equalsIgnoreCase(method)) {
            return false;
        }

        return true;
    }

    /**
     * 检查http请求是否是请求的传入的二进制数据，对于octet-stream，image，multipart文件 都认为是二进制的
     *
     * @param request
     * @return
     */
    public static boolean isBinayBodyData(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType == null) {
            return false;
        }
        contentType = contentType.toLowerCase();

        // 判断Content-Type是否指定为流数据
        if (contentType.contains("stream")) {
            return true;
        }

        // 判断Content-Type是否指定为文件上传
        if (contentType.contains("multipart")) {
            return true;
        }

        // 判断Content-Type是否指定为图片
        if (contentType.contains("image")) {
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public static String getParameterMapString(HttpServletRequest request) {
        if (request == null){
            return StrUtils.EMPTY;
        }

        Map<String, String[]> map = request.getParameterMap();

        if (map == null || map.size() <= 0) {
            return StrUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder(100);
        // 是否首次拼接
        boolean bfirst = true;
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            for (String item : entry.getValue()) {
                if (!bfirst) {
                    sb.append("&");
                } else {
                    bfirst = false;
                }
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(item);
            }
        }

        return sb.toString();
    }

    /**
     * 获取请求url的应用映射部分url
     * 例如 demo-test-war/test/index.html,获得结果为/test/index.html
     * @param request
     * @return
     */
    public static String getReqAppPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        return getRemainingPath(uri, contextPath, true);
    }

    /**
     * 去除context path
     *
     * @param requestUri
     * @param mapping
     * @param ignoreCase
     * @return
     */
    protected static String getRemainingPath(String requestUri, String mapping, boolean ignoreCase) {
        int index1 = 0;
        int index2 = 0;
        for (; (index1 < requestUri.length()) && (index2 < mapping.length()); index1++, index2++) {
            char c1 = requestUri.charAt(index1);
            char c2 = mapping.charAt(index2);
            if (c1 == ';') {
                index1 = requestUri.indexOf('/', index1);
                if (index1 == -1) {
                    return null;
                }
                c1 = requestUri.charAt(index1);
            }
            if (c1 == c2) {
                continue;
            } else if (ignoreCase && (Character.toLowerCase(c1) == Character.toLowerCase(c2))) {
                continue;
            }
            return null;
        }
        if (index2 != mapping.length()) {
            return null;
        } else if (index1 == requestUri.length()) {
            return "";
        } else if (requestUri.charAt(index1) == ';') {
            index1 = requestUri.indexOf('/', index1);
        }
        return (index1 != -1 ? requestUri.substring(index1) : "");
    }
}
