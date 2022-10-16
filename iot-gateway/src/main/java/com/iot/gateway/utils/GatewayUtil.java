package com.iot.gateway.utils;

import cn.hutool.core.util.StrUtil;
import com.iot.common.exception.NotFoundException;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/16 15:00
 */
public class GatewayUtil {

    /**
     * @param request: ServerHttpRequest
     * @return Remote Ip
     * @description 获取远程客户端 IP
     * @date
     */
    public static String getRemoteIp(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "X-Real-IP".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        }
        return ip;
    }

    /**
     * @param request: ServerHttpRequest
     * @param key:     header key
     * @return request header value
     * @description 获取 Request Header
     * @date
     */
    public static String getRequestHeader(ServerHttpRequest request, String key) {
        String header = request.getHeaders().getFirst(key);
        if (!StrUtil.isNotBlank(header)) {
            throw new NotFoundException("Invalid request header of " + key);
        }
        return header;
    }

    /**
     * @param request: ServerHttpRequest
     * @param key: cookie key
     * @return request cookie value
     * @description 获取 Request Cookie
     * @date
     */
    public static String getRequestCookie(ServerHttpRequest request, String key) {
        HttpCookie cookie = request.getCookies().getFirst(key);
        if (null == cookie || !StrUtil.isNotBlank(cookie.getValue())) {
            throw new NotFoundException("Invalid request cookie of " + key);
        }
        return cookie.getValue();
    }
}
