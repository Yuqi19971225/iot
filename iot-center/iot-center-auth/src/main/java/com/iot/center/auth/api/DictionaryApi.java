package com.iot.center.auth.api;

import com.iot.api.center.auth.feign.DictionaryClient;
import com.iot.center.auth.service.DictionaryService;
import com.iot.common.bean.Dictionary;
import com.iot.common.bean.R;
import com.iot.common.constant.ServiceConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/11 20:54
 */
@Slf4j
@RestController
@RequestMapping(value = ServiceConstant.Auth.DICTIONARY_URL_PREFIX)
public class DictionaryApi implements DictionaryClient {

    @Resource
    private DictionaryService dictionaryService;

    @Override
    public R<List<Dictionary>> tenantDictionary() {
        try {
            List<Dictionary> list = dictionaryService.tenantDictionary();
            if (list != null) {
                return R.ok(list);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<List<Dictionary>> userDictionary(String tenantId) {
        try {
            List<Dictionary> dictionaryList = dictionaryService.userDictionary(tenantId);
            if (null != dictionaryList) {
                return R.ok(dictionaryList);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<List<Dictionary>> blackIpDictionary(String tenantId) {
        try {
            List<Dictionary> dictionaryList = dictionaryService.blackIpDictionary(tenantId);
            if (null != dictionaryList) {
                return R.ok(dictionaryList);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }
}
