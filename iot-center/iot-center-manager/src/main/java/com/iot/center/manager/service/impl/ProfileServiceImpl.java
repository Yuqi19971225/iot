package com.iot.center.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.ProfileMapper;
import com.iot.center.manager.service.ProfileService;
import com.iot.common.model.Profile;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备模板表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
public class ProfileServiceImpl extends ServiceImpl<ProfileMapper, Profile> implements ProfileService {

}
