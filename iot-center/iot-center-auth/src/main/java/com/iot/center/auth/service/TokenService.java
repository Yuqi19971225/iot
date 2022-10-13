package com.iot.center.auth.service;

import com.iot.center.auth.bean.TokenValid;

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

    /**
     * @param name:
     * @param salt:
     * @param token:
     * @return TokenValid
     * @description 检测用户的token是否有效
     * @date
     */
    TokenValid checkTokenValid(String name, String salt, String token);

    /**
     * @param name:
     * @return boolean
     * @description 注销用户token令牌
     * @date
     */
    boolean cancelToken(String name);
}
