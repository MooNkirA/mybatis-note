package com.moon.mybatisplus.samples.string.test;

import com.moon.mybatisplus.samples.string.entity.Order;
import com.moon.mybatisplus.samples.string.mapper.OrderMapper;
import com.moon.mybatisplus.samples.string.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

/**
 * 字符串ID生成测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-12 10:56
 * @description
 */
@SpringBootTest
public class IdStringTest {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderService orderService;

    @Test
    public void testInsertOne() {
        Order order = new Order();
        order.setAmt(BigInteger.TEN);
        orderMapper.insert(order);

        System.out.println("新增生成的字符串ID：" + order.getId());
    }

    /**
     * 批量插入
     */
    /*public void testBatch() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setId(i + "1");
            user.setName("靓仔" + i);
            user.setAge(18 + i);
            users.add(user);
        }
        boolean result = userService.saveBatch(users);
        Assertions.assertEquals(true, result);
    }*/

}
