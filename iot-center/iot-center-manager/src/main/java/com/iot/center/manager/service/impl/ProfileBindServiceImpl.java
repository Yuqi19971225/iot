package com.iot.center.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.ProfileBindMapper;
import com.iot.center.manager.service.ProfileBindService;
import com.iot.common.model.ProfileBind;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 设备与模版映射关联表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
public class ProfileBindServiceImpl extends ServiceImpl<ProfileBindMapper, ProfileBind> implements ProfileBindService {

    @Override
    public Set<String> selectProfileIdsByDeviceId(String deviceId) {
        return null;
    }
}
