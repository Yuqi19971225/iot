package com.iot.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ：FYQ
 * @description： Gateway Controller
 * @date ：2022/10/16 15:08
 */
@Controller
public class GatewayController {
    @GetMapping(value = "/")
    public String index() {
        return "index";
    }
}
