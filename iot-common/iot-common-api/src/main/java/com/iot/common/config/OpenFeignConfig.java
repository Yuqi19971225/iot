package com.iot.common.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author ：FYQ
 * @description： OpenFeign 配置
 * @date ：2022/10/7 14:30
 */
@Configuration
public class OpenFeignConfig {
    @Bean
    Logger.Level feignLevel() {
        return Logger.Level.FULL;
    }
}
