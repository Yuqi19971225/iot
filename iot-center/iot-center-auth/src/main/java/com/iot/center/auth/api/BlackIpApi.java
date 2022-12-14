package com.iot.center.auth.api;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iot.api.center.auth.feign.BlackIpClient;
import com.iot.center.auth.service.BlackIpService;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import com.iot.common.dto.BlackIpDto;
import com.iot.common.model.BlackIp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：FYQ
 * @description: Ip 黑名单 Feign Client 接口实现
 * @date ：2022/10/11 16:13
 */
@Slf4j
@RestController
@RequestMapping(value = ServiceConstant.Auth.BLACK_IP_URL_PREFIX)
public class BlackIpApi implements BlackIpClient {
    @Resource
    private BlackIpService blackIpService;

    @Override
    public R<BlackIp> add(BlackIp blackIp) {
        try {
            BlackIp add = blackIpService.add(blackIp);
            if (ObjectUtil.isNotNull(add)) {
                return R.ok(add);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Boolean> delete(String id) {
        try {
            boolean result = blackIpService.delete(id);
            return result ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }

    }

    @Override
    public R<BlackIp> update(BlackIp blackIp) {
        try {
            BlackIp update = blackIpService.update(blackIp);
            if (ObjectUtil.isNotNull(update)) {
                return R.ok(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<BlackIp> selectById(String id) {
        try {
            BlackIp blackIp = blackIpService.selectById(id);
            if (ObjectUtil.isNotNull(blackIp)) {
                return R.ok(blackIp);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail("Resource does not exist");
    }

    @Override
    public R<BlackIp> selectByIp(String ip) {
        try {
            BlackIp blackIp = blackIpService.selectByIp(ip);
            if (ObjectUtil.isNotNull(blackIp)) {
                return R.ok(blackIp);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail("Resource does not exist");
    }

    @Override
    public R<Page<BlackIp>> list(BlackIpDto blackIpDto) {
        try {
            if (ObjectUtil.isEmpty(blackIpDto)) {
                blackIpDto = new BlackIpDto();
            }
            Page<BlackIp> page = blackIpService.list(blackIpDto);
            if (ObjectUtil.isNotNull(page)) {
                return R.ok(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail("Resource does not exist");
    }

    @Override
    public R<Boolean> checkBlackIpValid(String ip) {
        try {
            return blackIpService.checkBlackIpValid(ip) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
}
