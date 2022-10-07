package com.iot.api.center.auth.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.auth.fallback.UserClientFallback;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.UserDto;
import com.iot.common.model.User;
import com.iot.common.valid.Insert;
import com.iot.common.valid.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

/**
 * @author ：FYQ
 * @description： 用户 FeignClient
 * @date ：2022/10/7 19:14
 */
@FeignClient(path = ServiceConstant.Auth.USER_URL_PREFIX, name = ServiceConstant.Auth.SERVICE_NAME, fallback = UserClientFallback.class)
public interface UserClient {
    /**
     *
     *
     * 3.修改user
     * 4.根据id修改user密码
     * 5.根据id查询user
     * 6.根据name查询user
     * 7.分页查询user
     * 8.检测用户是否存在
     */
    /**
     * @param user: User
     * @return R<User>
     * @description 新增User
     * @date
     */
    @PostMapping("/add")
    R<User> add(@Validated(Insert.class) @RequestBody User user);

    /**
     * @param id: 用户id
     * @return R<Boolean>
     * @description 根据id删除user
     * @date
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") String id);

    /**
     * @param user:
     * @return R<User>
     * @description 修改User
     * 支持：Enable,Password
     * 不支持：name
     * @date
     */
    @PostMapping("/update")
    R<User> update(@Validated(Update.class) @RequestBody User user);

    /**
     * @param id: 用户id
     * @return R<Boolean>
     * @description 根据id充值user密码
     * @date
     */
    @PostMapping("/reset/{id}")
    R<Boolean> resetPassword(@NotNull @PathVariable(value = "id") String id);

    /**
     * @param id: 用户id
     * @return R<User>
     * @description 根据用户id查询用户
     * @date
     */
    @GetMapping("/id/{id}")
    R<User> selectById(@NotNull @PathVariable(value = "id") String id);

    /**
     * @param name: 用户名
     * @return R<User>
     * @description 根据用户名查询用户
     * @date
     */
    @GetMapping("/name/{name}")
    R<User> selectByName(@NotNull @PathVariable(value = "name") String name);

    /**
     * @param userDto:
     * @return R<Page < User>>
     * @description 分页查询user
     * @date
     */
    @PostMapping("/list")
    R<Page<User>> list(@RequestBody(required = false) UserDto userDto);

    /**
     * @param name: 用户名
     * @return R<Boolean>
     * @description 根据用户名查询用户是否存在
     * @date
     */
    @GetMapping("/check/{name}")
    R<Boolean> checkUserValid(@NotNull @PathVariable(value = "name") String name);
}
