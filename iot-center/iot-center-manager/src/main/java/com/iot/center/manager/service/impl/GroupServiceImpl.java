package com.iot.center.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.GroupMapper;
import com.iot.center.manager.service.GroupService;
import com.iot.common.model.Group;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分组表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

}
