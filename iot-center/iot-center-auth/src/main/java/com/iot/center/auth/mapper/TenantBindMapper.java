package com.iot.center.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iot.common.model.TenantBind;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 租户关联表 Mapper 接口
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
@Mapper
public interface TenantBindMapper extends BaseMapper<TenantBind> {

}
