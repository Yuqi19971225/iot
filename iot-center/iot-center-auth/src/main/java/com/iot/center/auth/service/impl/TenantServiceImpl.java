package com.iot.center.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.center.auth.mapper.TenantMapper;
import com.iot.center.auth.service.TenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.common.bean.Pages;
import com.iot.common.constant.CacheConstant;
import com.iot.common.dto.TenantDto;
import com.iot.common.exception.DuplicateException;
import com.iot.common.exception.NotFoundException;
import com.iot.common.exception.ServiceException;
import com.iot.common.model.Tenant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Optional;

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

    @Resource
    private TenantMapper tenantMapper;

    @Override
    @Caching(
            put = {
                    @CachePut(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.ID, key = "#tenant.id", condition = "#result!=null"),
                    @CachePut(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.NAME, key = "#tenant.name", condition = "#result!=null")
            },
            evict = {
                    @CacheEvict(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.DIC, allEntries = true, condition = "#result!=null"),
                    @CacheEvict(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public Tenant add(Tenant tenant) {
        Tenant select = selectByName(tenant.getName());
        if (select != null) {
            throw new DuplicateException("The tenant already exists");
        }
        if (tenantMapper.insert(tenant) > 0) {
            return tenantMapper.selectById(tenant.getId());
        }
        throw new ServiceException("The tenant add failed");
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.ID, key = "#id", condition = "#result==true"),
                    @CacheEvict(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.NAME, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.DIC, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.LIST, allEntries = true, condition = "#result==true")
            }
    )
    public boolean delete(String id) {
        Tenant select = selectById(id);
        if (select == null) {
            throw new NotFoundException("The tenant does not exists");
        }
        return tenantMapper.deleteById(select) > 0;
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.ID, key = "#tenant.id", condition = "#result!=null"),
                    @CachePut(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.NAME, key = "#tenant.name", condition = "#result!=null")
            },
            evict = {
                    @CacheEvict(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.DIC, allEntries = true, condition = "#result!=null"),
                    @CacheEvict(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public Tenant update(Tenant tenant) {
        tenant.setName(null).setCreateTime(null);
        if (tenantMapper.updateById(tenant) > 0) {
            Tenant select = tenantMapper.selectById(tenant.getId());
            tenant.setName(select.getName());
            return select;
        }
        throw new ServiceException("Update tenant failed");

    }

    @Override
    @Cacheable(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.ID, key = "#id", unless = "#result==null")
    public Tenant selectById(String id) {
        return tenantMapper.selectById(id);
    }

    @Override
    @Cacheable(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.LIST, keyGenerator = "commonKeyGenerator")
    public Page<Tenant> list(TenantDto tenantDto) {
        if (Optional.ofNullable(tenantDto.getPage()).isPresent()) {
            tenantDto.setPage(new Pages());
        }
        return tenantMapper.selectPage(tenantDto.getPage().convert(), fuzzyQuery(tenantDto));
    }

    @Override
    public LambdaQueryWrapper<Tenant> fuzzyQuery(TenantDto tenantDto) {
        LambdaQueryWrapper<Tenant> queryWrapper = Wrappers.<Tenant>query().lambda();
        if (tenantDto != null) {
            if (StrUtil.isNotBlank(tenantDto.getName())) {
                queryWrapper.like(Tenant::getName, tenantDto.getName());
            }
        }
        return queryWrapper;
    }

    @Override
    @Cacheable(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.NAME, key = "#name", unless = "#result==null")
    public Tenant selectByName(String name) {
        LambdaQueryWrapper<Tenant> queryWrapper = Wrappers.<Tenant>query().lambda();
        queryWrapper.eq(Tenant::getName, name);
        Tenant tenant = tenantMapper.selectOne(queryWrapper);
        if (tenant == null) {
            throw new NotFoundException("The tenant does not exist");
        }
        return tenant;
    }
}
