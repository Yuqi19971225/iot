package com.iot.api.center.auth.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.auth.fallback.TenantClientFallback;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.TenantDto;
import com.iot.common.model.Tenant;
import com.iot.common.valid.Insert;
import com.iot.common.valid.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author ：FYQ
 * @description： 租户FeignClient
 * @date ：2022/10/8 20:55
 */
@FeignClient(path = ServiceConstant.Auth.TENANT_URL_PREFIX, name = ServiceConstant.Auth.SERVICE_NAME, fallbackFactory = TenantClientFallback.class)

public interface TenantClient {

    /**
     * @param tenant:
     * @return R<Tenant>
     * @description 新增tenant
     * @date
     */
    @PostMapping("/add")
    R<Tenant> add(@Validated(Insert.class) @RequestBody Tenant tenant);

    /**
     * @param id: tenant id
     * @return R<Boolean>
     * @description 根据id删除tenant
     * @date
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") String id);

    /**
     * @param tenant:
     * @return R<Tenant>
     * @description 修改tenant
     * 支持：Enable
     * 不支持：name
     * @date
     */
    @PostMapping("/update")
    R<Tenant> update(@Validated(Update.class) @RequestBody Tenant tenant);

    /**
     * @param id: tenant id
     * @return R<Tenant>
     * @description 根据id查询tenant
     * @date
     */
    @GetMapping("/id/{id}")
    R<Tenant> selectById(@NotNull @PathVariable(value = "id") String id);

    /**
     * @param name: tenant name
     * @return R<Tenant>
     * @description 根据Name查询tenant
     * @date
     */
    @GetMapping("/name/{name}")
    R<Tenant> selectByName(@NotNull @PathVariable(value = "name") String name);

    /**
     * @param tenantDto:
     * @return R<Page<Tenant>>
     * @description 分页查询tenant
     * @date
     */
    @PostMapping("/list")
    R<Page<Tenant>> list(@RequestBody(required = true) TenantDto tenantDto);

}
