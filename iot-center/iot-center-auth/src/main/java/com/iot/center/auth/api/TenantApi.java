package com.iot.center.auth.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.auth.feign.TenantClient;
import com.iot.center.auth.service.TenantService;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.TenantDto;
import com.iot.common.model.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：FYQ
 * @description： 用户 Feign Client 接口实现
 * @date ：2022/10/8 21:14
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.Auth.TENANT_URL_PREFIX)
public class TenantApi implements TenantClient {

    @Resource
    private TenantService tenantService;

    @Override
    public R<Tenant> add(Tenant tenant) {
        try {
            Tenant add = tenantService.add(tenant);
            if (add != null) {
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
            return tenantService.delete(id) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Tenant> update(Tenant tenant) {
        return null;
    }

    @Override
    public R<Tenant> selectById(String id) {
        return null;
    }

    @Override
    public R<Tenant> selectByName(String name) {
        return null;
    }

    @Override
    public R<Page<Tenant>> list(TenantDto tenantDto) {
        return null;
    }
}
