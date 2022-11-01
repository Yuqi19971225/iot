package com.iot.center.manager.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.iot.common.model.ProfileBind;

import java.util.Set;

/**
 * <p>
 * 设备与模版映射关联表 服务类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
public interface ProfileBindService extends IService<ProfileBind> {

    /**
     * @param deviceId: Device Id
     * @return Set<String>
     * @description 根据 设备ID 查询关联的 模版ID 集合
     * @date
     */
    Set<String> selectProfileIdsByDeviceId(String deviceId);

    /**
     * @param deviceId: Device Id
     * @return boolean
     * @description 根据 设备ID 删除关联的模版映射
     * @date
     */
    boolean deleteByDeviceId(String deviceId);

    /**
     * @param deviceId: Device Id
     * @param profileId: Profile Id
     * @return ProfileBind
     * @description 根据 设备ID 和 模版ID 查询关联的模版映射
     * @date
     */
    ProfileBind deleteByDeviceIdAndProfileId(String deviceId, String profileId);

    /**
     * @param profileId: Profile Id
     * @return Set<String> Device Id Set
     * @description 根据 模版ID 查询关联的 设备ID 集合
     * @date
     */
    Set<String> selectDeviceIdsByProfileId(String profileId);
}
