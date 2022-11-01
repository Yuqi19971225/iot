package com.iot.center.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.DeviceMapper;
import com.iot.center.manager.service.DeviceService;
import com.iot.center.manager.service.NotifyService;
import com.iot.center.manager.service.ProfileBindService;
import com.iot.center.manager.service.ProfileService;
import com.iot.common.bean.Pages;
import com.iot.common.constant.CommonConstant;
import com.iot.common.dto.DeviceDto;
import com.iot.common.dto.DriverDto;
import com.iot.common.exception.DuplicateException;
import com.iot.common.exception.NotFoundException;
import com.iot.common.exception.ServiceException;
import com.iot.common.model.Device;
import com.iot.common.model.Point;
import com.iot.common.model.ProfileBind;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private ProfileBindService profileBindService;

    @Resource
    private ProfileService profileService;

    @Resource
    private NotifyService notifyService;

    @Override
    public Device add(Device device) {
        try {
            selectByName(device.getName(), device.getTenantId());
            throw new DuplicateException("The device already exists");
        } catch (NotFoundException notFoundException) {
            if (deviceMapper.insert(device) > 0) {
                addProfileBind(device.getId(), device.getProfileIds());
                Device select = deviceMapper.selectById(device.getId());
                select.setProfileIds(device.getProfileIds());
                return select;
            }
        }
        throw new ServiceException("The device add failed");
    }

    @Override
    public boolean delete(String id) {
        selectById(id);
        profileBindService.deleteByDeviceId(id);
        return deviceMapper.deleteById(id) > 0;
    }

    @Override
    public Device update(Device device) {
        selectById(device.getId());

        Set<String> newProfileIds = null != device.getProfileIds() ? device.getProfileIds() : new HashSet<>();
        Set<String> oldProfileIds = profileBindService.selectProfileIdsByDeviceId(device.getId());

        //新增的模板
        Set<String> add = new HashSet<>(newProfileIds);
        add.removeAll(oldProfileIds);

        //删除的模板
        Set<String> delete = new HashSet<>(oldProfileIds);
        delete.removeAll(newProfileIds);

        addProfileBind(device.getId(), add);
        delete.forEach(profileId -> {
            profileBindService.deleteByDeviceIdAndProfileId(device.getId(), profileId);
        });

        device.setUpdateTime(null);
        if (deviceMapper.updateById(device) > 0) {
            Device select = deviceMapper.selectById(device.getId());
            select.setProfileIds(newProfileIds);
            device.setName(select.getName());
            return select;
        }
        throw new ServiceException("The device update failed");
    }

    @Override
    public Device selectById(String id) {
        Device device = deviceMapper.selectById(id);
        if (device == null) {
            throw new NotFoundException("The device does not exist");
        }
        return device.setProfileIds(profileBindService.selectProfileIdsByDeviceId(id));
    }

    @Override
    public Page<Device> list(DeviceDto deviceDto) {
        if (ObjectUtil.isNull(deviceDto.getPage())) {
            deviceDto.setPage(new Pages());
        }
        return deviceMapper.selectPageWithProfile(deviceDto.getPage().convert(), customFuzzyQuery(deviceDto), deviceDto.getProfiledId());
    }

    @Override
    public Device selectByName(String name, String tenantId) {
        LambdaQueryWrapper<Device> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Device::getName, name);
        queryWrapper.eq(Device::getTenantId, tenantId);
        Device device = deviceMapper.selectOne(queryWrapper);
        if (device == null) {
            throw new NotFoundException("The device does not exist");
        }
        return device.setProfileIds(profileBindService.selectProfileIdsByDeviceId(device.getId()));
    }

    @Override
    public List<Device> selectByDriverId(String driverId) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setDriverId(driverId);
        List<Device> devices = deviceMapper.selectList(fuzzyQuery(deviceDto));
        if (devices == null || devices.size() < 1) {
            throw new NotFoundException("The devices does not exist");
        }
        devices.forEach(device -> {
            device.setProfileIds(profileBindService.selectProfileIdsByDeviceId(device.getId()));
        });
        return devices;
    }

    @Override
    public List<Device> selectByProfileId(String profileId) {
        return selectByIds(profileBindService.selectDeviceIdsByProfileId(profileId));
    }

    @Override
    public List<Device> selectByIds(Set<String> ids) {
        List<Device> devices = deviceMapper.selectBatchIds(ids);
        if (CollectionUtil.isEmpty(devices)) {
            throw new NotFoundException("The devices does not exist");
        }
        devices.forEach(device -> {
            device.setProfileIds(profileBindService.selectProfileIdsByDeviceId(device.getId()));
        });
        return devices;
    }

    @Override
    public LambdaQueryWrapper<Device> fuzzyQuery(DeviceDto deviceDto) {
        LambdaQueryWrapper<Device> queryWrapper = Wrappers.<Device>query().lambda();
        if (ObjectUtil.isNotEmpty(deviceDto)) {
            queryWrapper.like(StrUtil.isNotBlank(deviceDto.getName()), Device::getName, deviceDto.getName());
            queryWrapper.eq(StrUtil.isNotEmpty(deviceDto.getDriverId()), Device::getDriverId, deviceDto.getDriverId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(deviceDto.getEnable()), Device::getEnable, deviceDto.getEnable());
            queryWrapper.eq(StrUtil.isNotEmpty(deviceDto.getTenantId()), Device::getTenantId, deviceDto.getTenantId());
        }
        return queryWrapper;
    }

    public LambdaQueryWrapper<Device> customFuzzyQuery(DeviceDto deviceDto) {
        QueryWrapper<Device> queryWrapper = Wrappers.query();
        queryWrapper.eq("dd.deleted", 0);
        if (ObjectUtil.isNotNull(deviceDto)) {
            queryWrapper.like(StrUtil.isNotEmpty(deviceDto.getName()), "dd.name", deviceDto.getName());
            queryWrapper.eq(StrUtil.isNotEmpty(deviceDto.getDriverId()), "dd.driver_id", deviceDto.getDriverId());
            queryWrapper.eq(ObjectUtil.isNotNull(deviceDto.getEnable()), "dd.enable", deviceDto.getEnable());
            queryWrapper.eq(StrUtil.isNotEmpty(deviceDto.getTenantId()), "dd.tenant_id", deviceDto.getTenantId());
        }
        return queryWrapper.lambda();
    }

    private void addProfileBind(String deviceId, Set<String> profileIds) {
        if (null != profileIds) {
            profileIds.forEach(profileId -> {
                try {
                    profileService.selectById(profileId);
                    profileBindService.add(new ProfileBind(profileId, deviceId));

                    List<Point> points = profileService.selectByProfileId(profileId);
                    //通知驱动新增位号
                    points.forEach(point -> {
                        notifyService.notifyDriverPoint(CommonConstant.Driver.Point.ADD, point);
                    });
                } catch (Exception ignored) {
                }
            });
        }
    }
}
