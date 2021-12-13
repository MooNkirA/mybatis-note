package com.moon.mybatisplus.samples.id.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moon.mybatisplus.samples.id.entity.Order;
import com.moon.mybatisplus.samples.id.mapper.OrderMapper;
import com.moon.mybatisplus.samples.id.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义ID生成测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-12 13:34
 * @description
 */
@SpringBootTest
public class IdGeneratorTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 批量插入
     */
    @Test
    public void testBatch() {
        List<Order> orders = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Order order = new Order();
            order.setAmt(BigInteger.valueOf(i + 100));
            orders.add(order);
        }

        boolean result = orderService.saveBatch(orders);
        System.out.println("批量插入结果：" + (result ? "成功" : "失败"));
        List<Order> allOrders = orderMapper.selectList(new QueryWrapper<>());
        allOrders.forEach(System.out::println);
    }

}
