package com.moon.mybatisplus.samples.injector.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 扩展 BaseMapper 方法的方式1。
 * 其他 Mapper 继承此接口，也能获取相应的扩展
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 20:02
 * @description
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    List<T> findAll();

    int truncateAll();
}
