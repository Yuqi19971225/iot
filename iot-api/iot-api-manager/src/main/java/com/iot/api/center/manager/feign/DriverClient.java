package com.iot.api.center.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.manager.fallback.DriverClientFallback;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.DriverDto;
import com.iot.common.model.Driver;
import com.iot.common.valid.Insert;
import com.iot.common.valid.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

/**
 * @author ：FYQ
 * @description： 驱动 FeignClient
 * @date ：2022/11/2 20:22
 */
@FeignClient(name = ServiceConstant.Manager.SERVICE_NAME, path = ServiceConstant.Manager.DRIVER_URL_PREFIX, fallbackFactory = DriverClientFallback.class)
public interface DriverClient {

    /**
     * @param driver:   Driver
     * @param tenantId: tenantId
     * @return R<Driver>
     * @description 新增 Driver
     * @date
     */
    @PostMapping("/add")
    R<Driver> add(
            @Validated(Insert.class)
            @RequestBody Driver driver,
            @RequestHeader(value = ServiceConstant.Header.X_AUTH_TENANT_ID, defaultValue = "-1")
                    String tenantId);

    /**
     * @param id:
     * @return R<Boolean>
     * @description 根据ID删除
     * @date
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") String id);

    /**
     * @param driver:   Driver
     * @param tenantId:
     * @return R<Driver>
     * @description 修改 Driver
     * @date
     */
    @PostMapping("/update")
    R<Driver> update(@Validated(Update.class)
                     @RequestBody Driver driver,
                     @RequestHeader(value = ServiceConstant.Header.X_AUTH_TENANT_ID, defaultValue = "-1")
                             String tenantId);

    /**
     * @param id:
     * @return R<Driver>
     * @description 根据 ID 查询 Driver
     * @date
     */
    @GetMapping("/id/{id}")
    R<Driver> selectById(@NotNull @PathVariable(value = "id") String id);

    /**
     * @param driverIds: Driver Id Set
     * @return R<Map < String, Driver>>
     * @description 根据 ID 集合查询 Driver
     * @date
     */
    @PostMapping("/ids")
    R<Map<String, Driver>> selectByIds(@RequestBody Set<String> driverIds);

    /**
     * @param serviceName: Driver Service Name
     * @return R<Driver>
     * @description 根据 SERVICENAME 查询 Driver
     * @date
     */
    @GetMapping("/service/{serviceName}")
    R<Driver> selectByServiceName(@NotNull @PathVariable(value = "serviceName") String serviceName);

    /**
     * @param type:     Driver type
     * @param host:     Driver Host
     * @param port:     Driver Port
     * @param tenantId:
     * @return R<Driver>
     * @description 根据 TYPE & HOST & PORT 查询 Driver
     * @date
     */
    @GetMapping("/type/{type}/host/{host}/port/{port}")
    R<Driver> selectByHostPort(
            @NotNull @PathVariable(value = "type") String type,
            @NotNull @PathVariable(value = "host") String host,
            @NotNull @PathVariable(value = "port") Integer port,
            @RequestHeader(value = ServiceConstant.Header.X_AUTH_TENANT_ID, defaultValue = "-1") String tenantId);


    /**
     * @param driverDto: Driver Dto
     * @param tenantId:
     * @return R<Page<Driver>>
     * @description 分页查询 Driver
     * @date
     */
    @PostMapping("/list")
    R<Page<Driver>> list(
            @RequestBody(required = false) DriverDto driverDto,
            @RequestHeader(value = ServiceConstant.Header.X_AUTH_TENANT_ID, defaultValue = "-1") String tenantId);
}
