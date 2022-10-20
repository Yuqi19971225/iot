package com.iot.api.center.auth.feign;

import com.iot.api.center.auth.fallback.TokenClientFallback;
import com.iot.common.bean.Login;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.valid.Auth;
import com.iot.common.valid.Check;
import com.iot.common.valid.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ：FYQ
 * @description： 令牌Feign Client
 * @date ：2022/10/12 19:14
 */
@FeignClient(path = ServiceConstant.Auth.TOKEN_URL_PREFIX, name = ServiceConstant.Auth.SERVICE_NAME, fallbackFactory = TokenClientFallback.class)
public interface TokenClient {

    /**
     * @param login:
     * @return R<String>
     * @description 生成用户随机salt
     * @date
     */
    @PostMapping("/salt")
    R<String> generateSalt(@Validated(Update.class) @RequestBody Login login);

    /**
     * @param login:
     * @return R<String>
     * @description 生成用户随机token
     * @date
     */
    @PostMapping("/generate")
    R<String> generateToken(@Validated(Auth.class) @RequestBody Login login);

    /**
     * @param login :
     * @return R<Long>
     * @description 检测token是否有效
     * @date
     */
    @PostMapping("/check")
    R<String> checkTokenValid(@Validated(Check.class) @RequestBody Login login);

    /**
     * @param login:
     * @return R<Boolean>
     * @description 注销用户token令牌
     * @date
     */
    @PostMapping("/cancel")
    R<Boolean> cancelToken(@Validated(Update.class) @RequestBody Login login);

}
