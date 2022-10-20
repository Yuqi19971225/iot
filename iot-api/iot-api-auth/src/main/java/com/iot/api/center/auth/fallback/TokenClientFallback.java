package com.iot.api.center.auth.fallback;

import com.iot.api.center.auth.feign.TokenClient;
import com.iot.common.bean.Login;
import com.iot.common.bean.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/12 19:19
 */
@Slf4j
@Component
public class TokenClientFallback implements FallbackFactory<TokenClient> {
    @Override
    public TokenClient create(Throwable cause) {
        String message = cause.getMessage() == null ? "No available server for client: IOT-CENTER-AUTH" : cause.getMessage();
        log.error("Fallback:{}", message);
        return new TokenClient() {
            @Override
            public R<String> generateSalt(Login login) {
                return R.fail(message);
            }

            @Override
            public R<String> generateToken(Login login) {
                return R.fail(message);
            }

            @Override
            public R<String> checkTokenValid(Login login) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> cancelToken(Login login) {
                return R.fail(message);
            }
        };
    }
}
