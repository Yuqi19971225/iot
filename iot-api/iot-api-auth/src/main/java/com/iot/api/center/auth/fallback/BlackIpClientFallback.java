package com.iot.api.center.auth.fallback;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.auth.feign.BlackIpClient;
import com.iot.common.bean.R;
import com.iot.common.dto.BlackIpDto;
import com.iot.common.model.BlackIp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/11 15:48
 */
@Slf4j
@Component
public class BlackIpClientFallback implements FallbackFactory<BlackIpClient> {


    @Override
    public BlackIpClient create(Throwable cause) {
        String message = cause.getMessage() == null ? "No available server for client: DC3-CENTER-AUTH" : cause.getMessage();
        log.error("Fallback:{}", message);
        return new BlackIpClient() {
            @Override
            public R<BlackIp> add(BlackIp blackIp) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(String id) {
                return R.fail(message);
            }

            @Override
            public R<BlackIp> update(BlackIp blackIp) {
                return R.fail(message);
            }

            @Override
            public R<BlackIp> selectById(String id) {
                return R.fail(message);
            }

            @Override
            public R<BlackIp> selectByIp(String ip) {
                return R.fail(message);
            }

            @Override
            public R<Page<BlackIp>> list(BlackIpDto blackIpDto) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> checkBlackIpValid(String ip) {
                return R.fail(message);
            }
        };
    }
}
