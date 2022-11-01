package com.iot.center.manager.service.impl;

import com.iot.center.manager.service.DriverService;
import com.iot.center.manager.service.NotifyService;
import com.iot.common.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：FYQ
 * @description： NotifyService Impl
 * @date ：2022/10/27 21:46
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

    @Resource
    private DriverService driverService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void notifyDriverProfile(String command, Profile profile) {
        try {
            List<Driver> drivers = driverService.selectByProfileId(profile.getId());
        }
    }

    @Override
    public void notifyDriverPoint(String command, Point point) {

    }

    @Override
    public void notifyDriverDevice(String command, Device device) {

    }

    @Override
    public void notifyDriverDriverInfo(String command, Device device) {

    }

    @Override
    public void notifyDriverPointInfo(String command, PointInfo pointInfo) {

    }
}
