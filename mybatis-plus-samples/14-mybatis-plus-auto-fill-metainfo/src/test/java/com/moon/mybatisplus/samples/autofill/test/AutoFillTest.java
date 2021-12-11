package com.moon.mybatisplus.samples.autofill.test;

import com.moon.mybatisplus.samples.autofill.entity.User;
import com.moon.mybatisplus.samples.autofill.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 自动填充测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-11 21:15
 * @description
 */
@SpringBootTest
public class AutoFillTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testAutoFill() {
        User user = new User();
        user.setName("香风智乃");
        user.setAge(12);
        userMapper.insert(user);
        // 不设置，新增时自动填充默认值
        System.out.println("无设置roleId新增后：" + userMapper.selectById(user.getId()));

        User user2 = new User();
        user2.setName("夜神月");
        user2.setAge(17);
        user2.setRoleId(2L);
        userMapper.insert(user2);
        // 设置roleId，新增时为设置的roleId
        System.out.println("有设置roleId新增后：" + userMapper.selectById(user2.getId()));
    }

}
