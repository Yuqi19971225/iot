package com.iot.center.auth.service;

import com.iot.common.base.Service;
import com.iot.common.dto.UserDto;
import com.iot.common.model.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
public interface UserService extends Service<User, UserDto> {

    /**
     * @param name: 用户名
     * @param isEx: 抛异常
     * @return User
     * @description TODO
     * @date
     */
    User selectByName(String name, boolean isEx);

    /**
     * @param phone: 手机号
     * @param isEx:  抛异常
     * @return User
     * @description 根据手机号查询用户
     * @date
     */
    User selectByPhone(String phone, boolean isEx);

    /**
     * @param email: Email
     * @param isEx:  抛异常
     * @return User
     * @description 根据邮箱查询用户
     * @date
     */
    User selectByEmail(String email, boolean isEx);

    /**
     * @param name:
     * @return boolean
     * @description 根据用户名判断用户是否存在
     * @date
     */
    boolean checkUserValid(String name);

    /**
     * @param id:
     * @return boolean
     * @description 重置密码
     * @date
     */
    boolean resetPassword(String id);
}
