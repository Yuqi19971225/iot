package com.iot.center.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.auth.mapper.TenantBindMapper;
import com.iot.center.auth.service.TenantBindService;
import com.iot.common.dto.TenantBindDto;
import com.iot.common.exception.NotFoundException;
import com.iot.common.model.TenantBind;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    private TenantBindMapper tenantBindMapper;

    @Override
    public TenantBind selectByTenantIdAndUserId(String tenantId, String userId) {
        LambdaQueryWrapper<TenantBind> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                .eq(TenantBind::getTenantId, tenantId)
                .eq(TenantBind::getUserId, userId);
        TenantBind tenantBind = tenantBindMapper.selectOne(queryWrapper);
        if (tenantBind == null) {
            throw new NotFoundException("The tenant bind does not exist");
        }
        return tenantBind;
    }

    @Override
    public TenantBind add(TenantBind type) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public TenantBind update(TenantBind type) {
        return null;
    }

    @Override
    public TenantBind selectById(String id) {
        return null;
    }

    @Override
    public Page<TenantBind> list(TenantBindDto dto) {
        return null;
    }

    @Override
    public LambdaQueryWrapper<TenantBind> fuzzyQuery(TenantBindDto dto) {
        return null;
    }
}
