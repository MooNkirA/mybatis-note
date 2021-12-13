package com.moon.mybatisplus.samples.id.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.mybatisplus.samples.id.entity.Order;
import com.moon.mybatisplus.samples.id.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
 * 订单业务层
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-12 10:55
 * @description
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {
}
