package com.iot.center.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iot.common.base.Service;
import com.iot.common.dto.TenantBindDto;
import com.iot.common.model.Tenant;
import com.iot.common.model.TenantBind;

/**
 * <p>
 * 租户关联表 服务类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
public interface TenantBindService extends Service<TenantBind, TenantBindDto> {

    /**
     * @param tenantId:
     * @param userId:
     * @return TenantBind
     * @description 根据租户id和用户id查询
     * @date
     */
    TenantBind selectByTenantIdAndUserId(String tenantId, String userId);
}
