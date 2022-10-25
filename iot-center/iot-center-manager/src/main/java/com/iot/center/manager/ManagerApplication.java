package com.iot.center.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/25 20:18
 */
@SpringBootApplication
@EnableTransactionManagement
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
