package com.iot.center.register.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/14 21:41
 */
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/eureka/images/**").addResourceLocations("classpath:/static/images/");
    }
}
