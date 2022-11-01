package com.iot.center.manager.api;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.manager.feign.DeviceClient;
import com.iot.center.manager.service.DeviceService;
import com.iot.center.manager.service.NotifyService;
import com.iot.common.bean.R;
import com.iot.common.constant.CommonConstant;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.DeviceDto;
import com.iot.common.model.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ：FYQ
 * @description： 设备Client接口实现
 * @date ：2022/10/27 21:31
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.Manager.DEVICE_URL_PREFIX)
public class DeviceApi implements DeviceClient {

    @Resource
    private DeviceService deviceService;

    @Resource
    private NotifyService notifyService;

    @Override
    public R<Device> add(Device device, String tenantId) {
        try {
            Device add = deviceService.add(device.setTenantId(tenantId));
            if (ObjectUtil.isNotNull(add)) {
                notifyService.notifyDriverDevice(CommonConstant.Driver.Device.ADD, add);
                return R.ok(add);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Boolean> delete(String id) {
        try {
            Device device = deviceService.selectById(id);
            if (device != null && deviceService.delete(id)) {
                //通知驱动删除设备
                notifyService.notifyDriverDevice(CommonConstant.Driver.Device.DELETE, device);
                return R.ok();
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Device> update(Device device, String tenantId) {
        try {
            Device update = deviceService.update(device.setTenantId(tenantId));
            if (ObjectUtil.isNotNull(update)) {
                //通知驱动更新设备
                notifyService.notifyDriverDevice(CommonConstant.Driver.Device.UPDATE, update);
                return R.ok(update);
            }
        } catch (Exception e) {
            return R.ok(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Device> selectById(String id) {
        try {
            Device select = deviceService.selectById(id);
            if (ObjectUtil.isNotNull(select)) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Map<String, Device>> selectByIds(Set<String> deviceIds) {
        try {
            List<Device> devices = deviceService.selectByIds(deviceIds);
            Map<String, Device> deviceMap = devices.stream().collect(Collectors.toMap(Device::getId, Function.identity()));
            return R.ok(deviceMap);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Page<Device>> List(DeviceDto deviceDto, String tenantId) {
        try {
            if (ObjectUtil.isEmpty(deviceDto)) {
                deviceDto = new DeviceDto();
            }
            deviceDto.setTenantId(tenantId);
            Page<Device> page = deviceService.list(deviceDto);
            if (ObjectUtil.isNotNull(page)) {
                return R.ok(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }
}
