/*
 * Copyright 2022 Pnoker All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iot.gateway.utils;

import cn.hutool.core.util.StrUtil;
import com.iot.common.exception.NotFoundException;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;

/**
 * @author pnoker
 */
public class GatewayUtil {

    /**
     * 获取远程客户端 IP
     *
     * @param request ServerHttpRequest
     * @return Remote Ip
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
     * 获取 Request Header
     *
     * @param request ServerHttpRequest
     * @param key     header key
     * @return request header value
     */
    public static String getRequestHeader(ServerHttpRequest request, String key) {
        String header = request.getHeaders().getFirst(key);
        if (!StrUtil.isNotEmpty(header)) {
            throw new NotFoundException("Invalid request header of " + key);
        }
        return header;
    }

    /**
     * 获取 Request Cookie
     *
     * @param request ServerHttpRequest
     * @param key     cookie key
     * @return request cookie value
     */
    public static String getRequestCookie(ServerHttpRequest request, String key) {
        HttpCookie cookie = request.getCookies().getFirst(key);
        if (null == cookie || !StrUtil.isNotEmpty(cookie.getValue())) {
            throw new NotFoundException("Invalid request cookie of " + key);
        }
        return cookie.getValue();
    }
}
