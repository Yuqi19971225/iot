package com.iot.center.auth.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/13 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthHeader {
    private String user;
    private String salt;
    private String token;
}
