package com.iot.api.center.manager.fallback;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.manager.feign.DeviceClient;
import com.iot.common.bean.R;
import com.iot.common.dto.DeviceDto;
import com.iot.common.model.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/25 21:29
 */
@Slf4j
@Component
public class DeviceClientFallback implements FallbackFactory<DeviceClient> {
    @Override
    public DeviceClient create(Throwable cause) {
        String message = cause.getMessage();
        log.error("Fallback:{}", message);

        return new DeviceClient() {
            @Override
            public R<Device> add(Device device, String tenantId) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(String id) {
                return R.fail(message);
            }

            @Override
            public R<Device> update(Device device, String tenantId) {
                return R.fail(message);
            }

            @Override
            public R<Device> selectById(String id) {
                return R.fail(message);
            }

            @Override
            public R<Map<String, Device>> selectByIds(Set<String> deviceIds) {
                return R.fail(message);
            }

            @Override
            public R<Page<Device>> List(DeviceDto deviceDto, String tenantId) {
                return R.fail(message);
            }
        };
    }
}
