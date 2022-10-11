package com.iot.api.center.auth.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.auth.fallback.BlackIpClientFallback;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.BlackIpDto;
import com.iot.common.model.BlackIp;
import com.iot.common.valid.Insert;
import com.iot.common.valid.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

/**
 * @author ：FYQ
 * @description： IP 黑名单FeignClient
 * @date ：2022/10/11 15:42
 */
@FeignClient(path = ServiceConstant.Auth.BLACK_IP_URL_PREFIX, name = ServiceConstant.Auth.SERVICE_NAME, fallbackFactory = BlackIpClientFallback.class)
public interface BlackIpClient {

    /**
     * @param blackIp: Black Ip
     * @return R<BlackIp> BlacK Ip
     * @description 新增黑名单ip
     * @date
     */
    @PostMapping("/add")
    R<BlackIp> add(@Validated(Insert.class) @RequestBody BlackIp blackIp);

    /**
     * @param id:
     * @return R<Boolean>
     * @description 根据id删除
     * @date
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") String id);

    /**
     * @param blackIp:
     * @return R<BlackIp>
     * @description 修改 Black Ip
     * 支持enable
     * 不支持ip
     * @date
     */
    @PostMapping("/update")
    R<BlackIp> update(@Validated(Update.class) @RequestBody BlackIp blackIp);

    /**
     * @param id:black ip id
     * @return R<BlackIp>
     * @description 根据ID查询black ip
     * @date
     */
    @GetMapping("/id/{id}")
    R<BlackIp> selectById(@NotNull @PathVariable(value = "id") String id);

    /**
     * @param ip: black ip ip
     * @return R<BlackIp>
     * @description 根据ip查询Black ip
     * @date
     */
    @GetMapping("/ip/{ip}")
    R<BlackIp> selectByIp(@NotNull @PathVariable(value = "ip") String ip);

    /**
     * @param blackIpDto:
     * @return R<Page < BlackIp>>
     * @description 分页查询
     * @date
     */
    @PostMapping("/list")
    R<Page<BlackIp>> list(@RequestBody(required = true) BlackIpDto blackIpDto);

    /**
     * @param ip:
     * @return R<Boolean>
     * @description 查询ip是否在黑名单中
     * @date
     */
    @GetMapping("/check/ip")
    R<Boolean> checkBlackIpValid(@NotNull @PathVariable(value = "ip") String ip);

}
