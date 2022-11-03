package com.iot.center.manager.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.DriverMapper;
import com.iot.center.manager.service.DeviceService;
import com.iot.center.manager.service.DriverService;
import com.iot.common.bean.Pages;
import com.iot.common.constant.CommonConstant;
import com.iot.common.dto.DriverDto;
import com.iot.common.exception.DuplicateException;
import com.iot.common.exception.NotFoundException;
import com.iot.common.exception.ServiceException;
import com.iot.common.model.Device;
import com.iot.common.model.Driver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Resource
    private DriverMapper driverMapper;

    @Resource
    private DeviceService deviceService;

    @Override
    public Driver add(Driver driver) {
        try {
            selectByServiceName(driver.getServiceName());
            throw new DuplicateException("The driver already exists");
        } catch (NotFoundException notFoundException) {
            if (driverMapper.insert(driver) > 0) {
                return driverMapper.selectById(driver.getId());
            }
            throw new ServiceException("The driver add failed");
        }
    }

    @Override
    public boolean delete(String id) {
        selectById(id);
        return driverMapper.deleteById(id) > 0;
    }

    @Override
    public Driver update(Driver driver) {
        selectById(driver.getId());
        driver.setUpdateTime(null);
        if (driverMapper.updateById(driver) > 0) {
            Driver select = driverMapper.selectById(driver.getId());
            driver.setServiceName(select.getServiceName()).setHost(select.getHost()).setPort(select.getPort());
            return select;
        }
        throw new ServiceException("The driver update failed");
    }

    @Override
    public Driver selectById(String id) {
        Driver driver = driverMapper.selectById(id);
        if (driver == null) {
            throw new NotFoundException("The driver does not exist");
        }
        return driver;
    }

    @Override
    public Page<Driver> list(DriverDto driverDto) {
        if (driverDto.getPage() == null) {
            driverDto.setPage(new Pages());
        }
        return driverMapper.selectPage(driverDto.getPage().convert(), fuzzyQuery(driverDto));
    }

    @Override
    public LambdaQueryWrapper<Driver> fuzzyQuery(DriverDto driverDto) {
        LambdaQueryWrapper<Driver> queryWrapper = Wrappers.<Driver>query().lambda();
        if (ObjectUtil.isNotNull(driverDto)) {
            queryWrapper.like(StrUtil.isNotEmpty(driverDto.getName()), Driver::getName, driverDto.getName());
            queryWrapper.like(StrUtil.isNotEmpty(driverDto.getServiceName()), Driver::getServiceName, driverDto.getServiceName());
            queryWrapper.like(StrUtil.isNotEmpty(driverDto.getHost()), Driver::getHost, driverDto.getHost());
            queryWrapper.eq(ObjectUtil.isNotNull(driverDto.getPort()), Driver::getPort, driverDto.getPort());
            if (StrUtil.isEmpty(driverDto.getType())) {
                driverDto.setType(CommonConstant.Driver.Type.DRIVER);
            }
            queryWrapper.like(Driver::getType, driverDto.getType());
            queryWrapper.eq(ObjectUtil.isNotNull(driverDto.getEnable()), Driver::getEnable, driverDto.getEnable());
            queryWrapper.eq(StrUtil.isNotEmpty(driverDto.getTenantId()), Driver::getTenantId, driverDto.getTenantId());
        }
        return queryWrapper;
    }


    @Override
    public Driver selectByServiceName(String serviceName) {
        LambdaQueryWrapper<Driver> queryWrapper = Wrappers.<Driver>query().lambda();
        queryWrapper.eq(StrUtil.isNotEmpty(serviceName), Driver::getServiceName, serviceName);
        Driver driver = driverMapper.selectOne(queryWrapper);
        if (driver == null) {
            throw new NotFoundException("The driver does not exist");
        }
        return driver;
    }

    @Override
    public Driver selectByHostPort(String type, String host, Integer port, String tenantId) {
        LambdaQueryWrapper<Driver> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Driver::getType, type);
        queryWrapper.eq(Driver::getHost, host);
        queryWrapper.eq(Driver::getPort, port);
        queryWrapper.eq(Driver::getTenantId, tenantId);
        Driver driver = driverMapper.selectOne(queryWrapper);
        if (driver == null) {
            throw new NotFoundException("The driver does not exist");
        }
        return driver;
    }

    @Override
    public Driver selectByDeviceId(String deviceId) {
        deviceService.selectById(deviceId);
        return selectById(deviceId);
    }

    @Override
    public List<Driver> selectByIds(Set<String> ids) {
        List<Driver> drivers = driverMapper.selectBatchIds(ids);
        if (CollectionUtil.isEmpty(drivers)) {
            throw new NotFoundException("The driver does not exist");
        }
        return drivers;
    }

    @Override
    public List<Driver> selectByProfileId(String profiledId) {
        List<Device> devices = deviceService.selectByProfileId(profiledId);
        Set<String> driverIds = devices.stream().map(Device::getDriverId).collect(Collectors.toSet());
        return selectByIds(driverIds);
    }
}
