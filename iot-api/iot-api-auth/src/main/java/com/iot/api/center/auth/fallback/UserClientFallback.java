package com.iot.api.center.auth.fallback;

import com.iot.api.center.auth.feign.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author ：FYQ
 * @description： UserClientFallback
 * @date ：2022/10/7 19:16
 */
@Slf4j
@Component
public class UserClientFallback implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause) {
        return null;
    }
}
