package com.iot.api.center.manager.fallback;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.manager.feign.DriverClient;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.DriverDto;
import com.iot.common.model.Driver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author ：FYQ
 * @description： DriverClientFallback
 * @date ：2022/11/2 20:23
 */
@Slf4j
@Component
public class DriverClientFallback implements FallbackFactory<DriverClient> {
    @Override
    public DriverClient create(Throwable cause) {
        String message = cause.getMessage();
        log.error("Fallback:{}", message);
        return new DriverClient() {
            @Override
            public R<Driver> add(Driver driver, String tenantId) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(String id) {
                return R.fail(message);
            }

            @Override
            public R<Driver> update(Driver driver, String tenantId) {
                return R.fail(message);
            }

            @Override
            public R<Driver> selectById(String id) {
                return R.fail(message);
            }

            @Override
            public R<Map<String, Driver>> selectByIds(Set<String> driverIds) {
                return R.fail(message);
            }

            @Override
            public R<Driver> selectByServiceName(String serviceName) {
                return R.fail(message);
            }

            @Override
            public R<Driver> selectByHostPort(String type, String host, Integer port, String tenantId) {
                return R.fail(message);
            }

            @Override
            public R<Page<Driver>> list(DriverDto driverDto, String tenantId) {
                return R.fail(message);
            }
        };
    }
}
