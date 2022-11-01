package com.iot.api.center.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.manager.fallback.DeviceClientFallback;
import com.iot.common.bean.Pages;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.DeviceDto;
import com.iot.common.model.Device;
import com.iot.common.valid.Insert;
import com.iot.common.valid.Update;
import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

/**
 * @author ：FYQ
 * @description： 设备 FeignClient
 * @date ：2022/10/25 21:27
 */
@FeignClient(path = ServiceConstant.Manager.DEVICE_URL_PREFIX, name = ServiceConstant.Manager.SERVICE_NAME, fallbackFactory = DeviceClientFallback.class)
public interface DeviceClient {

    /**
     * @param device:   Device
     * @param tenantId:
     * @return R<Device>
     * @description 新增Device
     * @date
     */
    @PostMapping("/add")
    R<Device> add(
            @Validated(Insert.class)
            @RequestBody Device device,
            @RequestHeader(value = ServiceConstant.Header.X_AUTH_TENANT_ID, defaultValue = "-1") String tenantId
    );

    /**
     * @param id: Device Id
     * @return R<Boolean>
     * @description 根据ID删除Device
     * @date
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(
            @NotNull
            @PathVariable(value = "id") String id
    );

    /**
     * @param device:   Device
     * @param tenantId:
     * @return R<Device>
     * @description 修改Device
     * @date
     */
    @PostMapping("/update")
    R<Device> update(
            @Validated(Update.class)
            @RequestBody Device device,
            @RequestHeader(value = ServiceConstant.Header.X_AUTH_TENANT_ID, defaultValue = "-1") String tenantId
    );

    /**
     * @param id: Device Id
     * @return R<Device>
     * @description 根据 ID 查询 Device
     * @date
     */
    @GetMapping("/id/{id}")
    R<Device> selectById(
            @NotNull
            @PathVariable(value = "id") String id
    );

    /**
     * @param deviceIds: Device Id Set
     * @return R<Map < String, Device>>
     * @description 根据 ID 集合查询 Device
     * @date
     */
    @PostMapping("/ids")
    R<Map<String, Device>> selectByIds(@RequestBody Set<String> deviceIds);


    /**
     * @param deviceDto: Device Dto
     * @param tenantId:
     * @return R<Page<Device>>
     * @description 页查询 Device
     * @date
     */
    @PostMapping("/list")
    R<Page<Device>> List(
            @RequestBody(required = false) DeviceDto deviceDto,
            @RequestHeader(value = ServiceConstant.Header.X_AUTH_TENANT_ID, defaultValue = "-1") String tenantId
    );
}
