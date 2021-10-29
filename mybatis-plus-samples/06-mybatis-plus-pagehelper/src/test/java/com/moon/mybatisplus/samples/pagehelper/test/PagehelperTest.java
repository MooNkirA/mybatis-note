package com.moon.mybatisplus.samples.pagehelper.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.moon.mybatisplus.samples.pagehelper.entity.User;
import com.moon.mybatisplus.samples.pagehelper.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * pagehelper 分页助手测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-29 15:09
 * @description
 */
@SpringBootTest
public class PagehelperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testPagehelperBasic() {
        // 开启分页
        PageHelper.startPage(1, 2);
        PageInfo<User> users = new PageInfo<>(userMapper.selectList(new QueryWrapper<User>().gt("age", 20)));
        System.out.println("总页数pages：" + users.getPages());
        System.out.println("总记录数total：" + users.getTotal());
        users.getList().forEach(System.out::println);
    }

    @Test
    public void testDoSelectPageInfo() {
        // 开启分页
        PageInfo<User> users = PageHelper.startPage(1, 2)
                .doSelectPageInfo(() -> userMapper.selectList(new QueryWrapper<User>().gt("age", 20)));
        System.out.println("总页数pages：" + users.getPages());
        System.out.println("总记录数total：" + users.getTotal());
        users.getList().forEach(System.out::println);
    }

}
