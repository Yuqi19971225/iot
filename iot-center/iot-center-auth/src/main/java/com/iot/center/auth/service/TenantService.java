package com.iot.center.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iot.common.base.Service;
import com.iot.common.dto.TenantDto;
import com.iot.common.model.Tenant;

/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
public interface TenantService extends Service<Tenant, TenantDto> {

    /**
     * @param name: tenant name
     * @return Tenant
     * @description 根据tenant name 查询
     * @date
     */
    Tenant selectByName(String name);
}
