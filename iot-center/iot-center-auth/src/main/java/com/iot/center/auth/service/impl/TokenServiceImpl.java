package com.iot.center.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.iot.center.auth.bean.TokenValid;
import com.iot.center.auth.bean.UserLimit;
import com.iot.center.auth.service.TenantBindService;
import com.iot.center.auth.service.TenantService;
import com.iot.center.auth.service.TokenService;
import com.iot.center.auth.service.UserService;
import com.iot.common.constant.CacheConstant;
import com.iot.common.constant.CommonConstant;
import com.iot.common.exception.ServiceException;
import com.iot.common.model.Tenant;
import com.iot.common.model.User;
import com.iot.common.utils.IotUtil;
import com.iot.common.utils.KeyUtil;
import com.iot.common.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ：FYQ
 * @description： TokenService 实现类
 * @date ：2022/10/12 19:34
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private TenantService tenantService;
    @Resource
    private TenantBindService tenantBindService;
    @Resource
    private UserService userService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public String generateSalt(String userName) {
        String redisSaltKey = CacheConstant.Entity.USER + CacheConstant.Suffix.SALT + CommonConstant.Symbol.SEPARATOR + userName;
        String salt = redisUtil.getKey(redisSaltKey, String.class);
        if (StrUtil.isBlank(salt)) {
            salt = RandomUtil.randomString(16);
            redisUtil.setKey(redisSaltKey, salt, CacheConstant.Timeout.SALT_CACHE_TIMEOUT, TimeUnit.MINUTES);
        }
        return salt;

    }

    @Override
    public String generateToken(String tenant, String name, String salt, String password) {
        checkUserLimit(name);
        Tenant tempTenant = tenantService.selectByName(tenant);
        User tempUser = userService.selectByName(name, false);
        if (tempTenant.getEnable() && tempUser.getEnable()) { //租户和用户都可用
            tenantBindService.selectByTenantIdAndUserId(tempTenant.getId(), tempUser.getId());
            String redisKey = CacheConstant.Entity.USER + CacheConstant.Suffix.SALT + CommonConstant.Symbol.SEPARATOR + name;
            String tempSalt = redisUtil.getKey(redisKey, String.class);
            if (StrUtil.isNotBlank(tempSalt) && tempSalt.equals(salt)) { //redis中的salt与传入的salt相同
                if (IotUtil.md5(password).equals(tempUser.getPassword())) {
                    String redisTokenKey = CacheConstant.Entity.USER + CacheConstant.Suffix.TOKEN + CommonConstant.Symbol.SEPARATOR + name;
                    String token = KeyUtil.generateToken(name, tempSalt);
                    redisUtil.setKey(redisTokenKey, token, CacheConstant.Timeout.TOKEN_CACHE_TIMEOUT, TimeUnit.HOURS);
                    return token;
                }
            }
        }
        updateUserLimit(name, true);
        throw new ServiceException("Invalid tenant、username、password");
    }

    @Override
    public TokenValid checkTokenValid(String name, String salt, String token) {
        String redisKey = CacheConstant.Entity.USER + CacheConstant.Suffix.TOKEN + CommonConstant.Symbol.SEPARATOR + name;
        String redisToken = redisUtil.getKey(redisKey, String.class);
        if (StrUtil.isBlank(redisToken) || !redisToken.equals(token)) {
            return new TokenValid(false, null);
        }
        try {
            Claims claims = KeyUtil.parserToken(name, salt, token);
            return new TokenValid(true, claims.getExpiration());
        } catch (Exception e) {
            return new TokenValid(false, null);
        }
    }

    @Override
    public boolean cancelToken(String name) {
        String redisKey = CacheConstant.Entity.USER + CacheConstant.Suffix.TOKEN + CommonConstant.Symbol.SEPARATOR + name;
        redisUtil.deleteKey(redisKey);
        return true;
    }

    /**
     * @param username:
     * @return void
     * @description 检测用户是否登录限制，返回该用户是否受限
     * @date
     */
    private void checkUserLimit(String username) {
        String redisKey = CacheConstant.Entity.USER + CacheConstant.Suffix.LIMIT + CommonConstant.Symbol.SEPARATOR + username;
        UserLimit limit = redisUtil.getKey(redisKey, UserLimit.class);
        if (limit != null && limit.getTimes() >= 5) {
            Date now = new Date();
            long interval = limit.getExpireTime().getTime() - now.getTime();
            if (interval > 0) {
                limit = updateUserLimit(username, false);
                throw new ServiceException("Access restricted，Please try again after {}", IotUtil.formatCompleteData(limit.getExpireTime()));
            }
        }
    }

    /**
     * @param username   :
     * @param expireTime :
     * @return UserLimit:
     * @description 更新用户登录限制
     * @date
     */
    private UserLimit updateUserLimit(String username, boolean expireTime) {
        //登录验证失效时间
        int amount = CacheConstant.Timeout.USER_LIMIT_TIMEOUT;
        //设置redisKey
        String redisKey = CacheConstant.Entity.USER + CacheConstant.Suffix.LIMIT + CommonConstant.Symbol.SEPARATOR + username;
        //从redis缓存中获取登录限制对象，如果为空则new
        UserLimit userLimit = Optional.ofNullable(redisUtil.getKey(redisKey, UserLimit.class)).orElse(new UserLimit(0, new Date()));
        if (userLimit.getTimes() > 20) {
            //TODO 拉黑IP和锁定用户操作，然后通过Gateway进行拦截
            amount = 24 * 60;
        } else if (userLimit.getTimes() > 5) {
            amount = CacheConstant.Timeout.USER_LIMIT_TIMEOUT * userLimit.getTimes();
        }
        if (expireTime) { //失效了则推迟时间
            userLimit.setExpireTime(IotUtil.expireTime(amount, Calendar.MINUTE));
        }
        redisUtil.setKey(redisKey, userLimit, 1, TimeUnit.DAYS);
        return userLimit;
    }
}
