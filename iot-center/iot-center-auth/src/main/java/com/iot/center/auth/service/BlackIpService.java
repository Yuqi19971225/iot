package com.iot.center.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iot.common.base.Service;
import com.iot.common.dto.BlackIpDto;
import com.iot.common.model.BlackIp;

/**
 * <p>
 * Ip黑名单表 服务类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
public interface BlackIpService extends Service<BlackIp, BlackIpDto> {

    /**
     * @param ip: black ip
     * @return BlackIp
     * @description 根据Ip查询black ip
     * @date
     */
    BlackIp selectByIp(String ip);

    /**
     * @param ip:
     * @return Boolean
     * @description 根据 Ip 是否在Ip黑名单列表
     * @date
     */
    Boolean checkBlackIpValid(String ip);
}
