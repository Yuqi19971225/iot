package com.iot.common.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author ：FYQ
 * @description：基础服务类接口
 * @date ：2022/10/6 21:32
 */
public interface Service<T, D> {

    /**
     * @param type:
     * @return T
     * @description 新增
     * @date
     */
    T add(T type);

    /**
     * @param id:
     * @return boolean
     * @description 根据id删除
     * @date
     */
    boolean delete(String id);

    /**
     * @param type:
     * @return T
     * @description 更新
     * @date
     */
    T update(T type);

    /**
     * @param id:
     * @return T
     * @description 根据id查询
     * @date
     */
    T selectById(String id);

    /**
     * @param dto:
     * @return Page<T>
     * @description 获取带分页、排序
     * @date
     */
    Page<T> list(D dto);

    /**
     * @param dto:
     * @return LambdaQueryWrapper<T>
     * @description 统一接口 模糊查询构造器
     * @date
     */
    LambdaQueryWrapper<T> fuzzyQuery(D dto);

}
