package com.iot.center.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/14 21:39
 */
@SpringBootApplication
@EnableEurekaServer
public class RegisterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class, args);
    }
}
