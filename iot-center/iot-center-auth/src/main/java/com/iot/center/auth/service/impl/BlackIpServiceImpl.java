package com.iot.center.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.center.auth.mapper.BlackIpMapper;
import com.iot.center.auth.service.BlackIpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.common.bean.Pages;
import com.iot.common.constant.CacheConstant;
import com.iot.common.dto.BlackIpDto;
import com.iot.common.exception.DuplicateException;
import com.iot.common.exception.NotFoundException;
import com.iot.common.exception.ServiceException;
import com.iot.common.model.BlackIp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p>
 * Ip黑名单表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
@Slf4j
@Service
public class BlackIpServiceImpl extends ServiceImpl<BlackIpMapper, BlackIp> implements BlackIpService {

    @Resource
    private BlackIpMapper blackIpMapper;

    @Override
    @Caching(
            put = {
                    @CachePut(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.ID, key = "#blackIp.id", condition = "#result!=null"),
                    @CachePut(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.IP, key = "#blackId.ip", condition = "#result !=null")
            },
            evict = {
                    @CacheEvict(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public BlackIp add(BlackIp blackIp) {
        BlackIp select = selectByIp(blackIp.getId());
        if (ObjectUtil.isNotNull(select)) {
            throw new ServiceException("The ip already exists in the blacklist");
        }
        if (baseMapper.insert(blackIp) > 0) {
            return baseMapper.selectById(blackIp.getId());
        }
        throw new ServiceException("The ip add to the blacklist failed");
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.ID, key = "#id", condition = "#result==true"),
                    @CacheEvict(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.IP, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.LIST, allEntries = true, condition = "#result==true")
            }
    )
    public boolean delete(String id) {
        BlackIp select = selectById(id);
        if (select == null) {
            throw new NotFoundException("The ip does not exists");
        }
        return blackIpMapper.deleteById(select) > 0;
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.ID, key = "#blackIp.id", condition = "#result!=null"),
                    @CachePut(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.IP, key = "#blackIp.ip", condition = "#result!=null")
            },
            evict = {
                    @CacheEvict(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public BlackIp update(BlackIp blackIp) {
        blackIp.setIp(null).setUpdateTime(null);
        if (blackIpMapper.updateById(blackIp) > 0) {
            BlackIp select = blackIpMapper.selectById(blackIp.getId());
            select.setIp(blackIp.getIp());
            return select;
        }
        throw new ServiceException("The ip update failed in the blacklist");
    }

    @Override
    @Cacheable(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.ID, key = "#id", condition = "#result!=null")
    public BlackIp selectById(String id) {
        return blackIpMapper.selectById(id);
    }

    @Override
    @Cacheable(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.LIST, keyGenerator = "commonKeyGenerator", unless = "#result==null")
    public Page<BlackIp> list(BlackIpDto blackIpDto) {
        if (Optional.ofNullable(blackIpDto.getPage()).isPresent()) {
            blackIpDto.setPage(new Pages());
        }
        return blackIpMapper.selectPage(blackIpDto.getPage().convert(), fuzzyQuery(blackIpDto));
    }

    @Override
    public LambdaQueryWrapper<BlackIp> fuzzyQuery(BlackIpDto blackIpDto) {
        LambdaQueryWrapper<BlackIp> queryWrapper = Wrappers.<BlackIp>query().lambda();
        if (blackIpDto != null) {
            if (StrUtil.isNotBlank(blackIpDto.getIp())) {
                queryWrapper.like(BlackIp::getIp, blackIpDto.getIp());
            }
        }
        return queryWrapper;
    }

    @Override
    @Cacheable(value = CacheConstant.Entity.BLACK_IP + CacheConstant.Suffix.IP, key = "#ip", unless = "#result==null")
    public BlackIp selectByIp(String ip) {
        LambdaQueryWrapper<BlackIp> queryWrapper = Wrappers.<BlackIp>query().lambda();
        queryWrapper.eq(BlackIp::getIp, ip);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean checkBlackIpValid(String ip) {
        BlackIp select = selectByIp(ip);
        if (ObjectUtil.isNotNull(select)) {
            return select.getEnable();
        }
        return false;
    }
}
