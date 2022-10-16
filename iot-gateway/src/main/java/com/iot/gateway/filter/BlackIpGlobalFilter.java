package com.iot.gateway.filter;

import com.iot.api.center.auth.feign.BlackIpClient;
import com.iot.common.bean.R;
import com.iot.gateway.utils.GatewayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author ：FYQ
 * @description： 自定义Ip黑名单过滤器
 * @date ：2022/10/16 14:52
 */
@Slf4j
public class BlackIpGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private BlackIpClient blackIpClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String remoteIp = GatewayUtil.getRemoteIp(request);

        R<Boolean> blackIpValid = blackIpClient.checkBlackIpValid(remoteIp);
        if (blackIpValid.isOk()) {
            log.error("Forbidden Ip: {}", remoteIp);
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        log.info("Remote Ip: {}; Request url: {}; Response code: {}", remoteIp, request.getURI().getRawPath(), exchange.getResponse().getStatusCode());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
