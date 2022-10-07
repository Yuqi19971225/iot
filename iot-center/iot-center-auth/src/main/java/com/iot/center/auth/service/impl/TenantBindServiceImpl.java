package com.iot.center.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.auth.mapper.TenantBindMapper;
import com.iot.center.auth.service.TenantBindService;
import com.iot.common.model.TenantBind;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户关联表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
@Service
public class TenantBindServiceImpl extends ServiceImpl<TenantBindMapper, TenantBind> implements TenantBindService {

}
