package com.moon.mybatisplus.test;

import com.moon.mybatisplus.entity.User;
import com.moon.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Mybatis Plus 快速入门示例
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-19 23:12
 * @description
 */
@SpringBootTest
public class BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assertions.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}
