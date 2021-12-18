package com.moon.mybatisplus.samples.string.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.mybatisplus.samples.string.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * order 表持久接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-12 10:55
 * @description
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
