package com.iot.center.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.PointInfoMapper;
import com.iot.center.manager.service.PointInfoService;
import com.iot.common.model.PointInfo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 位号配置信息表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
public class PointInfoServiceImpl extends ServiceImpl<PointInfoMapper, PointInfo> implements PointInfoService {

}
