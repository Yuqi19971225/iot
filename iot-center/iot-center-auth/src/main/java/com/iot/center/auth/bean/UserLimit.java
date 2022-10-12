package com.iot.center.auth.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ：FYQ
 * @description： 用户登录限制
 * @date ：2022/10/12 20:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserLimit {

    /**
     *  登录验证错误次数
     */
    private Integer times;

    /**
     *  验证失效时间
     */
    private Date expireTime;
}
