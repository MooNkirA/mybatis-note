package com.moon.mybatisplus.samples.quickstart.test;

import com.moon.mybatisplus.samples.quickstart.entity.User;
import com.moon.mybatisplus.samples.quickstart.mapper.UserMapper;
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
        userList.forEach(System.out::println);
        System.out.println("总记录数：" + userList.size());
    }

}
