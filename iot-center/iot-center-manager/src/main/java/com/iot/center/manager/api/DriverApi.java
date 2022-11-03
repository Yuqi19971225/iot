package com.iot.center.manager.api;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.manager.feign.DriverClient;
import com.iot.center.manager.service.DriverService;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.DriverDto;
import com.iot.common.model.Driver;
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
 * @description：驱动 Client 接口实现
 * @date ：2022/11/2 20:51
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.Manager.DRIVER_URL_PREFIX)
public class DriverApi implements DriverClient {

    @Resource
    private DriverService driverService;

    @Override
    public R<Driver> add(Driver driver, String tenantId) {
        try {
            Driver add = driverService.add(driver);
            if (ObjectUtil.isNotNull(add)) {
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
            return driverService.delete(id) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Driver> update(Driver driver, String tenantId) {
        try {
            Driver update = driverService.update(driver.setTenantId(tenantId));
            if (ObjectUtil.isNotNull(update)) {
                return R.ok(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Driver> selectById(String id) {
        try {
            Driver select = driverService.selectById(id);
            if (ObjectUtil.isNotNull(select)) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Map<String, Driver>> selectByIds(Set<String> driverIds) {
        try {
            List<Driver> drivers = driverService.selectByIds(driverIds);
            Map<String, Driver> driverMap = drivers.stream().collect(Collectors.toMap(Driver::getId, Function.identity()));
            return R.ok(driverMap);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Driver> selectByServiceName(String serviceName) {
        try {
            Driver select = driverService.selectByServiceName(serviceName);
            if (ObjectUtil.isNotNull(select)) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Driver> selectByHostPort(String type, String host, Integer port, String tenantId) {
        try {
            Driver select = driverService.selectByHostPort(type, host, port, tenantId);
            if (ObjectUtil.isNotNull(select)) {
                return R.ok(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<Driver>> list(DriverDto driverDto, String tenantId) {
        try {
            if (ObjectUtil.isEmpty(driverDto)) {
                driverDto = new DriverDto();
            }
            driverDto.setTenantId(tenantId);
            Page<Driver> page = driverService.list(driverDto);
            if (ObjectUtil.isNotNull(page)) {
                return R.ok(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }
}
