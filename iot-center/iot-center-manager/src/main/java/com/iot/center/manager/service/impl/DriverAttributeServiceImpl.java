package com.iot.center.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.DriverAttributeMapper;
import com.iot.center.manager.service.DriverAttributeService;
import com.iot.common.model.DriverAttribute;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 连接配置信息表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
public class DriverAttributeServiceImpl extends ServiceImpl<DriverAttributeMapper, DriverAttribute> implements DriverAttributeService {

}
