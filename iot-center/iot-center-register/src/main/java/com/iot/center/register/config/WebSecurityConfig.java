package com.iot.center.register.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/14 21:42
 */
@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        try {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/actuator/**").permitAll()
                    .anyRequest()
                    .authenticated().and().httpBasic();
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }
}
