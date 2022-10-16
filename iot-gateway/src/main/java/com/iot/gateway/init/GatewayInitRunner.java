package com.iot.gateway.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author ：FYQ
 * @description： 初始化
 * @date ：2022/10/16 15:09
 */
@Component
@EnableFeignClients(basePackages = {
        "com.iot.api.auth.*"
})
@ComponentScan(basePackages = {
        "com.iot.api.center.auth"
})
public class GatewayInitRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
