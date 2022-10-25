package com.iot.center.manager.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author ：FYQ
 * @description： 初始化
 * @date ：2022/10/25 20:41
 */
@Component
@EnableFeignClients(basePackages = {
        "com.iot.api.center.auth.*",
})
@ComponentScan(basePackages = {
        "com.iot.api.center.auth",
})
public class ManagerInitRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
