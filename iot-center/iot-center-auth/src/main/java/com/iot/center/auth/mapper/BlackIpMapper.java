package com.iot.center.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iot.common.model.BlackIp;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Ip黑名单表 Mapper 接口
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
@Mapper
public interface BlackIpMapper extends BaseMapper<BlackIp> {

}
