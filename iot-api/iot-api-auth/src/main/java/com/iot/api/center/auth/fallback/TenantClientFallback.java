package com.iot.api.center.auth.fallback;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.auth.feign.TenantClient;
import com.iot.common.bean.R;
import com.iot.common.dto.TenantDto;
import com.iot.common.model.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author ：FYQ
 * @description： TenantClientFallback
 * @date ：2022/10/8 20:58
 */
@Slf4j
@Component
public class TenantClientFallback implements FallbackFactory<TenantClient> {
    @Override
    public TenantClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: IOT-CENTER-AUTH" : throwable.getMessage();
        log.error("Fallback:{}", message);

        return new TenantClient() {
            @Override
            public R<Tenant> add(Tenant tenant) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(String id) {
                return R.fail(message);
            }

            @Override
            public R<Tenant> update(Tenant tenant) {
                return R.fail(message);
            }

            @Override
            public R<Tenant> selectById(String id) {
                return R.fail(message);
            }

            @Override
            public R<Tenant> selectByName(String name) {
                return R.fail(message);
            }

            @Override
            public R<Page<Tenant>> list(TenantDto tenantDto) {
                return R.fail(message);
            }
        };
    }
}
