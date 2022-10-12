package com.iot.center.auth.service;

/**
 * @author ：FYQ
 * @description： Token interface
 * @date ：2022/10/12 19:33
 */
public interface TokenService {
    /**
     * @param userName: 用户名
     * @return String
     * @description 生成用户随机salt
     * @date
     */
    String generateSalt(String userName);

    /**
     * @param tenant: Tenant
     * @param name: username
     * @param salt:  salt
     * @param password: password
     * @return String
     * @description 生成用户的token令牌
     * @date
     */
    String generateToken(String tenant, String name, String salt, String password);
}
