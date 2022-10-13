package com.iot.center.auth.api;

import com.iot.api.center.auth.feign.TokenClient;
import com.iot.center.auth.bean.TokenValid;
import com.iot.center.auth.service.TokenService;
import com.iot.common.bean.Login;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.exception.UnAuthorizedException;
import com.iot.common.utils.Dc3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * @author ：FYQ
 * @description： Token Feign Client 实现类
 * @date ：2022/10/12 19:31
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.Auth.TOKEN_URL_PREFIX)
public class TokenApi implements TokenClient {

    @Resource
    private TokenService tokenService;

    @Override
    public R<String> generateSalt(Login login) {
        String salt = tokenService.generateSalt(login.getName());
        return salt == null ? R.fail() : R.ok(salt, "The salt will expire in 5 minutes");
    }

    @Override
    public R<String> generateToken(Login login) {
        String token = tokenService.generateToken(login.getTenant(), login.getName(), login.getSalt(), login.getPassword());
        return token == null ? R.fail() : R.ok(token, "The token will expire in 12 hours");
    }

    @Override
    public R<Long> checkTokenValid(Login login) {
        TokenValid tokenValid = tokenService.checkTokenValid(login.getName(), login.getSalt(), login.getToken());
        if (tokenValid.isValid()) {
            return R.ok(tokenValid.getExpireTime().getTime(), "The token will expire in " + Dc3Util.formatCompleteData(tokenValid.getExpireTime()));
        }
        throw new UnAuthorizedException("Token invalid");
    }

    @Override
    public R<Boolean> cancelToken(Login login) {
        return tokenService.cancelToken(login.getName()) ? R.ok() : R.fail();
    }
}
