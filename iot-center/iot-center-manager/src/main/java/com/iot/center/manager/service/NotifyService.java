package com.iot.center.manager.service;

import com.iot.common.model.Device;
import com.iot.common.model.Point;
import com.iot.common.model.PointInfo;
import com.iot.common.model.Profile;

/**
 * @author ：FYQ
 * @description： Notify Interface
 * @date ：2022/10/27 21:45
 */
public interface NotifyService {

    /**
     * @param command: Operation Type
     * @param profile: Profile
     * @return void
     * @description 通知驱动 新增模板(ADD) / 删除模板(DELETE) / 修改模板(UPDATE)
     * @date
     */
    void notifyDriverProfile(String command, Profile profile);

    /**
     * @param command: Operation Type
     * @param point:   Point
     * @return void
     * @description 通知驱动 新增位号(ADD) / 删除位号(DELETE) / 修改位号(UPDATE)
     * @date
     */
    void notifyDriverPoint(String command, Point point);

    /**
     * @param command: Operation Type
     * @param device:  Device
     * @return void
     * @description 通知驱动 新增设备(ADD) / 删除设备(DELETE) / 修改设备(UPDATE)
     * @date
     */
    void notifyDriverDevice(String command, Device device);

    /**
     * @param command: Operation Type
     * @param device:  Driver Info
     * @return void
     * @description 通知驱动 新增驱动配置(ADD) / 删除驱动配置(DELETE) / 修改驱动配置(UPDATE)
     * @date
     */
    void notifyDriverDriverInfo(String command, Device device);

    /**
     * @param command:   Operation Type
     * @param pointInfo: PointInfo
     * @return void
     * @description 通知驱动 新增位号配置(ADD) / 删除位号配置(DELETE) / 更新位号配置(UPDATE)
     * @date
     */
    void notifyDriverPointInfo(String command, PointInfo pointInfo);
}
