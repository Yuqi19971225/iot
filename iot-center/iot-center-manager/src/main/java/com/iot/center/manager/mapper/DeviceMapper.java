package com.iot.center.manager.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.common.model.Device;

/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
public interface DeviceMapper extends BaseMapper<Device> {

    Page<Device> selectPageWithProfile(Page<Object> convert, LambdaQueryWrapper<Device> customFuzzyQuery, String profiledId);
}
