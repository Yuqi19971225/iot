package com.iot.center.manager.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.DriverMapper;
import com.iot.center.manager.service.DriverService;
import com.iot.common.model.Driver;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 协议驱动表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
public class DriverServiceImpl extends ServiceImpl<DriverMapper, Driver> implements DriverService {

}
