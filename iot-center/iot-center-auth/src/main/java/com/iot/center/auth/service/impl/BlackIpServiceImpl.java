package com.iot.center.auth.service.impl;

import com.iot.center.auth.mapper.BlackIpMapper;
import com.iot.center.auth.service.BlackIpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.common.model.BlackIp;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Ip黑名单表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
@Service
public class BlackIpServiceImpl extends ServiceImpl<BlackIpMapper, BlackIp> implements BlackIpService {

}
