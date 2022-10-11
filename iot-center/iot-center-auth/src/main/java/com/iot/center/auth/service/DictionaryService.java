package com.iot.center.auth.service;

import com.iot.common.bean.Dictionary;

import java.util.List;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/11 20:56
 */
public interface DictionaryService {
    /**
     * @param :
     * @return List<Dictionary>
     * @description 获取租户字典
     * @date
     */
    List<Dictionary> tenantDictionary();

    /**
     * @param tenantId:
     * @return List<Dictionary>
     * @description 获取用户字典
     * @date
     */
    List<Dictionary> userDictionary(String tenantId);

    /**
     * @param tenantId:
     * @return List<Dictionary>
     * @description 获取黑名单字典
     * @date
     */
    List<Dictionary> blackIpDictionary(String tenantId);
}
