package com.drone.management.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 请求日志拦截器
 * PreHandle  : 打印请求 URL、方法、参数、客户端 IP
 * PostHandle : 打印请求耗时
 */
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);

    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        START_TIME.set(System.currentTimeMillis());

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String ip = getClientIp(request);

        // 构建参数信息
        StringBuilder params = new StringBuilder();
        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap != null && !paramMap.isEmpty()) {
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                params.append(entry.getKey()).append("=");
                String[] values = entry.getValue();
                params.append(values != null && values.length > 0 ? values[0] : "");
                params.append("; ");
            }
        }

        log.info("[请求拦截] IP={} | {} {} | Query={} | Params=[{}]",
                ip, method, uri,
                queryString != null ? queryString : "",
                params.toString().trim());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
        long start = START_TIME.get();
        if (start > 0) {
            long elapsed = System.currentTimeMillis() - start;
            log.info("[请求完成] {} {} | 耗时={}ms | Status={}",
                    request.getMethod(), request.getRequestURI(),
                    elapsed, response.getStatus());
        }
        START_TIME.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        START_TIME.remove();
    }

    /** 获取客户端真实 IP */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级代理取第一个 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
