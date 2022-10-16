package com.iot.gateway.filter.factory;

import com.iot.api.center.auth.feign.TenantClient;
import com.iot.api.center.auth.feign.TokenClient;
import com.iot.common.bean.Login;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.exception.ServiceException;
import com.iot.common.model.Tenant;
import com.iot.common.utils.IotUtil;
import com.iot.common.utils.JsonUtil;
import com.iot.gateway.utils.GatewayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author ：FYQ
 * @description： 自定义 Request Header 校验过滤器
 * @date ：2022/10/16 10:27
 */
@Slf4j
@Component
public class AuthenticGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return new AuthenticGatewayFilter();
    }

    @Component
    static class AuthenticGatewayFilter implements GatewayFilter {
        private static AuthenticGatewayFilter gatewayFilter;

        @Resource
        private TenantClient tenantClient;
        @Resource
        private TokenClient tokenClient;

        @PostConstruct
        public void init() {
            gatewayFilter = this;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            ServerHttpRequest request = exchange.getRequest();

            try {
                String cookieToken = GatewayUtil.getRequestCookie(request, ServiceConstant.Header.X_AUTH_TOKEN);
                Login login = JsonUtil.parseObject(IotUtil.decode(cookieToken), Login.class);
                log.debug("Request cookies: {}", login);

                R<Tenant> tenantR = gatewayFilter.tenantClient.selectByName(login.getTenant());
                if (!tenantR.isOk() || !tenantR.getData().getEnable()) {
                    throw new ServiceException("Invalid tenant");
                }

                R<Long> validR = gatewayFilter.tokenClient.checkTokenValid(login);
                if (!validR.isOk()) {
                    throw new ServiceException("Invalid token");
                }
                Tenant tenant = tenantR.getData();
                log.debug("Request tenant: {}", tenant);

                ServerHttpRequest build = request.mutate().headers(
                        httpHeader -> {
                            httpHeader.set(ServiceConstant.Header.X_AUTH_TENANT_ID, tenant.getId());
                            httpHeader.set(ServiceConstant.Header.X_AUTH_TENANT, login.getTenant());
                            httpHeader.set(ServiceConstant.Header.X_AUTH_USER, login.getName());
                        }
                ).build();
                exchange.mutate().request(build).build();
            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                response.setStatusCode(HttpStatus.FORBIDDEN);
                log.error(e.getMessage(), e);

                DataBuffer dataBuffer = response.bufferFactory().wrap(JsonUtil.toJsonBytes(R.fail(e.getMessage())));
                return response.writeWith(Mono.just(dataBuffer));
            }

            return chain.filter(exchange);
        }
    }
}
