package com.moon.mybatisplus.samples.sequence.test;

import com.moon.mybatisplus.samples.sequence.entity.User;
import com.moon.mybatisplus.samples.sequence.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Sequence 序列生成测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 14:03
 * @description
 */
@SpringBootTest
public class SequenceTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSequenceGenerate() {
        User user = new User();
        user.setAge(18);
        user.setEmail("test@moon.com");
        user.setName("sequence");
        userMapper.insert(user);
        Long id1 = user.getId();
        System.out.println(id1);
        Assertions.assertTrue(id1 >= 1000, "sequence start with 1000");

        user = new User();
        user.setAge(19);
        user.setEmail("test2@moon.com");
        user.setName("sequence2");
        userMapper.insert(user);
        Long id2 = user.getId();
        Assertions.assertTrue(id2 - id1 == 1, "squence increment by 1");
    }

}
