package com.iot.center.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.center.manager.mapper.LabelBindMapper;
import com.iot.center.manager.service.LabelBindService;
import com.iot.common.model.LabelBind;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签关联表 服务实现类
 * </p>
 *
 * @author FYQ
 * @since 2022-10-25
 */
@Service
public class LabelBindServiceImpl extends ServiceImpl<LabelBindMapper, LabelBind> implements LabelBindService {

}
