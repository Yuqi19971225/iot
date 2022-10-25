package com.iot.center.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.PointMapper;
import com.iot.center.manager.service.PointService;
import com.iot.common.model.Point;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备位号表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
public class PointServiceImpl extends ServiceImpl<PointMapper, Point> implements PointService {

}
