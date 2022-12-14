package com.iot.center.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iot.common.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author FYQ
 * @since 2022-10-07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
