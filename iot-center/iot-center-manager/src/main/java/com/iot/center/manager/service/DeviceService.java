package com.iot.center.manager.service;


import com.iot.common.base.Service;
import com.iot.common.dto.DeviceDto;
import com.iot.common.model.Device;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
public interface DeviceService extends Service<Device, DeviceDto> {

    /**
     * @param name: Device Name
     * @param tenantId: Tenant Id
     * @return Device
     * @description 根据 设备Name 和 租户Id 查询设备
     * @date
     */
    Device selectByName(String name,String tenantId);

    /**
     * @param driverId: Driver Id
     * @return List<Device> Device Array
     * @description 根据 驱动Id 查询该驱动下的全部设备
     * @date
     */
    List<Device> selectByDriverId(String driverId);

    /**
     * @param profileId: Profile Id
     * @return List<Device> Device Array
     * @description 根据 模板Id 查询该驱动下的全部设备
     * @date
     */
    List<Device> selectByProfileId(String profileId);

    /**
     * @param ids: Device Id Set
     * @return List<Device> Device Array
     * @description 根据 设备Id集 查询设备
     * @date
     */
    List<Device> selectByIds(Set<String> ids);

}
