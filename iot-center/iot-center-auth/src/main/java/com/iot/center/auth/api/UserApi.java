package com.iot.center.auth.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.auth.feign.UserClient;
import com.iot.center.auth.service.UserService;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.UserDto;
import com.iot.common.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author ：FYQ
 * @description： 用户 Feign Client 接口实现
 * @date ：2022/10/7 19:41
 */
@Slf4j
@RestController
@RequestMapping(ServiceConstant.Auth.USER_URL_PREFIX)
public class UserApi implements UserClient {
    @Resource
    private UserService userService;

    @Override
    public R<User> add(User user) {
        try {
            User add = userService.add(user);
            if (add != null) {
                return R.ok(add);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Boolean> delete(String id) {
        try {
            return userService.delete(id) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<User> update(User user) {
        try {
            User update = userService.update(user.setName(null));
            if (update != null) {
                return R.ok(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Boolean> resetPassword(String id) {
        try {
            return userService.resetPassword(id) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<User> selectById(String id) {
        try {
            User user = userService.selectById(id);
            return user != null ? R.ok(user) : R.fail("Resource does not exist");
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<User> selectByName(String name) {
        try {
            User user = userService.selectByName(name, false);
            if (user != null) {
                return R.ok(user);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail("Resource does not exist");
    }

    @Override
    public R<Page<User>> list(UserDto userDto) {
        try {
            Page<User> list = userService.list(userDto);
            if (list != null) {
                return R.ok(list);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail("Resource does not exist");
    }

    @Override
    public R<Boolean> checkUserValid(String name) {
        try {
            return userService.checkUserValid(name) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
}
