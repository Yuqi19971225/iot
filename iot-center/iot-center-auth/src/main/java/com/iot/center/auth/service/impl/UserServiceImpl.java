package com.iot.center.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.center.auth.mapper.UserMapper;
import com.iot.center.auth.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.common.annotation.Logs;
import com.iot.common.constant.CacheConstant;
import com.iot.common.dto.UserDto;
import com.iot.common.exception.AddException;
import com.iot.common.exception.DuplicateException;
import com.iot.common.exception.EmptyException;
import com.iot.common.exception.NotFoundException;
import com.iot.common.model.User;
import com.iot.common.utils.Dc3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @Logs("Add user")
    @Transactional
    @Caching(
            put = {
                    @CachePut(value = CacheConstant.Entity.USER + CacheConstant.Suffix.ID, key = "#user.id", condition = "#result !=null"),
                    @CachePut(value = CacheConstant.Entity.USER + CacheConstant.Suffix.NAME, key = "#user.name", condition = "#result!=null"),
                    @CachePut(value = CacheConstant.Entity.USER + CacheConstant.Suffix.PHONE, key = "#user.phone", condition = "#result!=null&&#user.phone!=null"),
                    @CachePut(value = CacheConstant.Entity.USER + CacheConstant.Suffix.EMAIL, key = "#user.email", condition = "#result!=null&&#user.email!=null")
            },
            evict = {
                    @CacheEvict(value = CacheConstant.Entity.USER + CacheConstant.Suffix.DIC, allEntries = true, condition = "#result!=null"),
                    @CacheEvict(value = CacheConstant.Entity.USER + CacheConstant.Suffix.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public User add(User user) {
        //判断用户是否存在
        User selectByName = selectByName(user.getName(), false);
        if (ObjectUtil.isNotNull(selectByName)) {
            throw new DuplicateException("The user already exists with username: {}", user.getName());
        }

        //判断phone是否存在，如果不为空，检查该phone是否被占用
        if (StrUtil.isNotEmpty(user.getPhone())) {
            User selectByPhone = selectByPhone(user.getPhone(), false);
            if (ObjectUtil.isNotNull(selectByPhone)) {
                throw new DuplicateException("The user already exists with phone: {}", user.getPhone());
            }
        }

        // 判断 email 是否存在，如果有 email 不为空，检查该 email 是否被占用
        if (StrUtil.isNotEmpty(user.getEmail())) {
            User selectByEmail = selectByEmail(user.getEmail(), false);
            if (ObjectUtil.isNotNull(selectByEmail)) {
                throw new DuplicateException("The user already exists with email : {}", user.getEmail());
            }
        }

        // 插入 user 数据，并返回插入后的 user
        if (userMapper.insert(user.setPassword(Dc3Util.md5(user.getPassword()))) > 0) {
            return userMapper.selectById(user.getId());
        }

        throw new AddException("The user add failed: {}", user.toString());
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = CacheConstant.Entity.USER + CacheConstant.Suffix.ID, key = "#id", condition = "#result==true"),
                    @CacheEvict(value = CacheConstant.Entity.USER + CacheConstant.Suffix.NAME, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = CacheConstant.Entity.USER + CacheConstant.Suffix.PHONE, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = CacheConstant.Entity.USER + CacheConstant.Suffix.EMAIL, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = CacheConstant.Entity.USER + CacheConstant.Suffix.DIC, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = CacheConstant.Entity.USER + CacheConstant.Suffix.LIST, allEntries = true, condition = "#result==true")
            }
    )
    public boolean delete(String id) {
        User user = selectById(id);
        if (user == null) {
            throw new NotFoundException("The user does not exist");
        }
        return userMapper.deleteById(user) > 0;
    }

    @Override
    public User update(User type) {
        return null;
    }

    @Override
    @Cacheable(value = CacheConstant.Entity.USER + CacheConstant.Suffix.ID, key = "#id", unless = "#result==null")
    public User selectById(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public Page<User> list(UserDto dto) {
        return null;
    }

    @Override
    public LambdaQueryWrapper<User> fuzzyQuery(UserDto dto) {
        return null;
    }


    @Override
    @Cacheable(value = CacheConstant.Entity.USER + CacheConstant.Suffix.NAME, key = "#name", unless = "#result==null")
    public User selectByName(String name, boolean isEx) {
        if (StrUtil.isEmpty(name)) {
            if (isEx) {
                throw new EmptyException("The name is empty");
            }
            return null;
        }
        return selectByKey(User::getName, name, isEx);
    }

    @Override
    @Cacheable(value = CacheConstant.Entity.USER + CacheConstant.Suffix.PHONE, key = "#phone", unless = "#result==null")
    public User selectByPhone(String phone, boolean isEx) {
        if (StrUtil.isEmpty(phone)) {
            if (isEx) {
                throw new EmptyException("The phone is empty");
            }
            return null;
        }
        return selectByKey(User::getPhone, phone, isEx);
    }

    @Override
    @Cacheable(value = CacheConstant.Entity.USER + CacheConstant.Suffix.EMAIL, key = "#email", unless = "#result==null")
    public User selectByEmail(String email, boolean isEx) {
        if (StrUtil.isEmpty(email)) {
            if (isEx) {
                throw new EmptyException("The email is empty");
            }
            return null;
        }
        return selectByKey(User::getEmail, email, isEx);
    }

    //TODO
    @Override
    public boolean checkUserValid(String name) {
        return false;
    }

    //TODE
    @Override
    public boolean resetPassword(String id) {
        return false;
    }

    private User selectByKey(SFunction<User, ?> key, String value, boolean isEx) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>query().lambda();
        queryWrapper.eq(key, value);
        User user = userMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(user)) {
            if (isEx) {
                throw new NotFoundException("The user does not exit");
            }
            return null;
        }
        return user;

    }
}
