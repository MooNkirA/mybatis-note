package com.moon.mybatisplus.samples.injector.test;

import com.moon.mybatisplus.samples.injector.entity.User;
import com.moon.mybatisplus.samples.injector.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * SQL 注入器测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 20:45
 * @description
 */
@SpringBootTest
public class InjectorTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试自定义查找方法
     */
    @Test
    public void testCustomFindAll() {
        List<User> users = userMapper.findAll();
        users.forEach(System.out::println);
    }

    /**
     * 测试自定义删除表数据方法
     */
    @Test
    public void testCustomTruncateAll() {
        System.out.println(userMapper.truncateAll());
    }

}
