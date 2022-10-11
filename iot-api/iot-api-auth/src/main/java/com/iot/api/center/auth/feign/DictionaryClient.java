package com.iot.api.center.auth.feign;

import com.iot.api.center.auth.fallback.DictionaryClientFallback;
import com.iot.common.bean.Dictionary;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author ：FYQ
 * @description： 字典 Feign Client
 * @date ：2022/10/11 20:40
 */
@FeignClient(path = ServiceConstant.Auth.DICTIONARY_URL_PREFIX, name = ServiceConstant.Auth.SERVICE_NAME, fallbackFactory = DictionaryClientFallback.class)
public interface DictionaryClient {

    /**
     * @param :
     * @return R<List < Dictionary>>
     * @description 查询租户字典
     * @date
     */
    @GetMapping("/tenant")
    R<List<Dictionary>> tenantDictionary();

    /**
     * @param tenantId:
     * @return R<List < Dictionary>>
     * @description 查询用户 Dictionary
     * @date
     */
    @GetMapping("/user")
    R<List<Dictionary>> userDictionary(@RequestHeader(value = ServiceConstant.Header.X_AUTH_TENANT_ID, defaultValue = "-1") String tenantId);

    /**
     * @param tenantId:
     * @return R<List<Dictionary>>
     * @description 查询黑名单 Dictionary
     * @date
     */
    @GetMapping("/black_ip")
    R<List<Dictionary>> blackIpDictionary(@RequestHeader(value = ServiceConstant.Header.X_AUTH_TENANT_ID, defaultValue = "-1") String tenantId);
}
