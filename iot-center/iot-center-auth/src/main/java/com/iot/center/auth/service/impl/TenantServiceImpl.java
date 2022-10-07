package com.iot.center.auth.service.impl;

import com.iot.center.auth.mapper.TenantMapper;
import com.iot.center.auth.service.TenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.common.model.Tenant;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

}
