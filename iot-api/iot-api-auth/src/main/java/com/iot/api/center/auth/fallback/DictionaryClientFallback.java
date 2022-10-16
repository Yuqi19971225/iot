package com.iot.api.center.auth.fallback;

import com.iot.api.center.auth.feign.DictionaryClient;
import com.iot.common.bean.Dictionary;
import com.iot.common.bean.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/11 20:41
 */
@Slf4j
@Component
public class DictionaryClientFallback implements FallbackFactory<DictionaryClient> {
    @Override
    public DictionaryClient create(Throwable cause) {
        String message = cause.getMessage() == null ? "No available server for client: IOT-CENTER-AUTH" : cause.getMessage();
        log.error("fallback:{}", message);
        return new DictionaryClient() {
            @Override
            public R<List<Dictionary>> tenantDictionary() {
                return R.fail(message);
            }

            @Override
            public R<List<Dictionary>> userDictionary(String tenantId) {
                return R.fail(message);
            }

            @Override
            public R<List<Dictionary>> blackIpDictionary(String tenantId) {
                return R.fail(message);
            }
        };
    }
}
