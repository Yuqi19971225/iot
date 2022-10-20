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

package com.iot.gateway.filter.factory;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.iot.api.center.auth.feign.TenantClient;
import com.iot.api.center.auth.feign.TokenClient;
import com.iot.api.center.auth.feign.UserClient;
import com.iot.common.annotation.Logs;
import com.iot.common.bean.Login;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.exception.UnAuthorizedException;
import com.iot.common.model.Tenant;
import com.iot.common.model.User;
import com.iot.common.utils.IotUtil;
import com.iot.common.utils.JsonUtil;
import com.iot.gateway.bean.TokenRequestHeader;
import com.iot.gateway.utils.GatewayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
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
 * 自定义 Request Header 校验过滤器
 *
 * @author pnoker
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
        @Lazy
        private TenantClient tenantClient;

        @Resource
        @Lazy
        private UserClient userClient;

        @Resource
        @Lazy
        private TokenClient tokenClient;

        @PostConstruct
        public void init() {
            gatewayFilter = this;
        }

        @Override
        @Logs("Authentic Gateway Filter")
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            ServerHttpRequest request = exchange.getRequest();

            try {
                String tenantHeader = GatewayUtil.getRequestHeader(request, ServiceConstant.Header.X_AUTH_TENANT);
                String tenant = IotUtil.decodeString(tenantHeader);
                if (ObjectUtil.isEmpty(tenant)) {
                    throw new UnAuthorizedException("Invalid request tenant header");
                }
                R<Tenant> tenantR = gatewayFilter.tenantClient.selectByName(tenant);
                if (!tenantR.isOk() || !tenantR.getData().getEnable()) {
                    throw new UnAuthorizedException("Invalid request tenant header");
                }

                String userHeader = GatewayUtil.getRequestHeader(request, ServiceConstant.Header.X_AUTH_USER);
                String user = IotUtil.decodeString(userHeader);
                if (ObjectUtil.isEmpty(user)) {
                    throw new UnAuthorizedException("Invalid request user header");
                }
                R<User> userR = gatewayFilter.userClient.selectByName(user);
                if (!userR.isOk() || !userR.getData().getEnable()) {
                    throw new UnAuthorizedException("Invalid request user header");
                }

                String tokenHeader = GatewayUtil.getRequestHeader(request, ServiceConstant.Header.X_AUTH_TOKEN);
                TokenRequestHeader token = JsonUtil.parseObject(IotUtil.decode(tokenHeader), TokenRequestHeader.class);
                if (ObjectUtil.isEmpty(token) || !StrUtil.isAllNotEmpty(token.getSalt(), token.getToken())) {
                    throw new UnAuthorizedException("Invalid request token header");
                }
                Login login = new Login();
                login.setTenant(tenantR.getData().getName()).setName(userR.getData().getName()).setSalt(token.getSalt()).setToken(token.getToken());
                R<String> tokenR = gatewayFilter.tokenClient.checkTokenValid(login);
                if (!tokenR.isOk()) {
                    throw new UnAuthorizedException("Invalid request token header");
                }

                ServerHttpRequest build = request.mutate().headers(
                        httpHeader -> {
                            httpHeader.set(ServiceConstant.Header.X_AUTH_TENANT_ID, tenantR.getData().getId());
                            httpHeader.set(ServiceConstant.Header.X_AUTH_TENANT, tenantR.getData().getName());
                            httpHeader.set(ServiceConstant.Header.X_AUTH_USER_ID, userR.getData().getId());
                            httpHeader.set(ServiceConstant.Header.X_AUTH_USER, userR.getData().getName());
                        }
                ).build();

                exchange.mutate().request(build).build();
            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                log.error(e.getMessage(), e);

                DataBuffer dataBuffer = response.bufferFactory().wrap(JsonUtil.toJsonBytes(R.fail(e.getMessage())));
                return response.writeWith(Mono.just(dataBuffer));
            }

            return chain.filter(exchange);
        }
    }

}
