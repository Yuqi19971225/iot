package com.iot.center.manager.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.DriverMapper;
import com.iot.center.manager.service.DriverService;
import com.iot.common.dto.DriverDto;
import com.iot.common.model.Driver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 协议驱动表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
@Slf4j
public class DriverServiceImpl extends ServiceImpl<DriverMapper, Driver> implements DriverService {

    @Override
    public Driver add(Driver type) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Driver update(Driver type) {
        return null;
    }

    @Override
    public Driver selectById(String id) {
        return null;
    }

    @Override
    public Page<Driver> list(DriverDto dto) {
        return null;
    }

    @Override
    public LambdaQueryWrapper<Driver> fuzzyQuery(DriverDto dto) {
        return null;
    }


    @Override
    public Driver selectByServiceName(String serviceName) {
        return null;
    }

    @Override
    public Driver selectByHostPort(String type, String host, Integer port, String tenantId) {
        return null;
    }

    @Override
    public Driver selectByDeviceId(String deviceId) {
        return null;
    }

    @Override
    public List<Driver> selectByIds(Set<String> ids) {
        return null;
    }

    @Override
    public List<Driver> selectByProfileId(String profiledId) {
        return null;
    }
}
