package com.iot.center.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.LabelMapper;
import com.iot.center.manager.service.LabelService;
import com.iot.common.model.Label;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements LabelService {

}
