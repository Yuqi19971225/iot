package com.iot.center.auth.bean;

import com.iot.common.constant.ServiceConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @description：TODO
 * @author     ：FYQ
 * @date       ：2022/10/13 20:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TokenValid {
    private boolean valid;
    private Date expireTime;
}
