package com.iot.center.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iot.center.auth.mapper.BlackIpMapper;
import com.iot.center.auth.mapper.TenantMapper;
import com.iot.center.auth.mapper.UserMapper;
import com.iot.center.auth.service.DictionaryService;
import com.iot.common.bean.Dictionary;
import com.iot.common.constant.CacheConstant;
import com.iot.common.model.BlackIp;
import com.iot.common.model.Tenant;
import com.iot.common.model.User;
import org.springframework.cache.annotation.Cacheable;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/11 20:57
 */
public class DictionaryServiceImpl implements DictionaryService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private TenantMapper tenantMapper;
    @Resource
    private BlackIpMapper blackIpMapper;

    @Override
    @Cacheable(value = CacheConstant.Entity.TENANT + CacheConstant.Suffix.DIC, key = "'dic'", unless = "#result==null")
    public List<Dictionary> tenantDictionary() {
        List<Dictionary> tenantDictionary = new ArrayList<>(16);
        LambdaQueryWrapper<Tenant> queryWrapper = Wrappers.<Tenant>query().lambda();
        List<Tenant> tenants = tenantMapper.selectList(queryWrapper);
        for (Tenant tenant : tenants) {
            Dictionary driverDictionary = new Dictionary().setLabel(tenant.getName()).setValue(tenant.getId());
            tenantDictionary.add(driverDictionary);
        }
        return tenantDictionary;
    }

    @Override
    @Cacheable(value = CacheConstant.Entity.USER + CacheConstant.Suffix.DIC, key = "'dic.'+#tenandId", unless = "#result==null")
    public List<Dictionary> userDictionary(String tenantId) {
        List<Dictionary> userDictionary = new ArrayList<>(16);
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>query().lambda();
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            Dictionary driverDictionary = new Dictionary().setLabel(user.getName()).setValue(user.getId());
            userDictionary.add(driverDictionary);
        }
        return userDictionary;
    }

    @Override
    @Cacheable(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.DIC, key = "'dic.'+#tenantId", unless = "#result==null")
    public List<Dictionary> blackIpDictionary(String tenantId) {
        List<Dictionary> dictionaryList = new ArrayList<>(16);
        LambdaQueryWrapper<BlackIp> queryWrapper = Wrappers.<BlackIp>query().lambda();
        List<BlackIp> blackIpList = blackIpMapper.selectList(queryWrapper);
        for (BlackIp blackIp : blackIpList) {
            Dictionary driverDictionary = new Dictionary().setLabel(blackIp.getIp()).setValue(blackIp.getId());
            dictionaryList.add(driverDictionary);
        }
        return dictionaryList;
    }
}
