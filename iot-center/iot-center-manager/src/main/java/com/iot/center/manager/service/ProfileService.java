package com.iot.center.manager.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.iot.common.model.Point;
import com.iot.common.model.Profile;

import java.util.List;

/**
 * <p>
 * 设备模板表 服务类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
public interface ProfileService extends IService<Profile> {

    List<Point> selectByProfileId(String profileId);
}
