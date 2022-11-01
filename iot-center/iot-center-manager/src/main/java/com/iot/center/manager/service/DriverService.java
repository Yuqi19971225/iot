package com.iot.center.manager.service;


import com.iot.common.base.Service;
import com.iot.common.dto.DriverDto;
import com.iot.common.model.Driver;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 协议驱动表 服务类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
public interface DriverService extends Service<Driver, DriverDto> {

    /**
     * @param serviceName: Driver Service Name
     * @return Driver
     * @description 根据 驱动ServiceName 查询 驱动
     * @date
     */
    Driver selectByServiceName(String serviceName);

    /**
     * @param type: Driver Type
     * @param host: Driver Service Host
     * @param port: Driver Service Port
     * @param tenantId:
     * @return Driver
     * @description 根据 驱动 Host & Port 查询 驱动
     * @date
     */
    Driver selectByHostPort(String type, String host, Integer port, String tenantId);

    /**
     * @param deviceId: Device Id
     * @return Driver
     * @description 根据 驱动Id 查询 驱动
     * @date
     */
    Driver selectByDeviceId(String deviceId);

    /**
     * @param ids: Driver Id Array
     * @return List<Driver> Driver Array
     * @description 根据 驱动Id集 查询 驱动集
     * @date
     */
    List<Driver> selectByIds(Set<String> ids);

    /**
     * @param profiledId: Profile Id
     * @return List<Driver> Driver Array
     * @description 根据 模版Id 查询 驱动集
     * @date
     */
    List<Driver> selectByProfileId(String profiledId);
}
