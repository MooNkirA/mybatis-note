package com.moon.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.moon.mybatisplus.entity.User;
import com.moon.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * mybatis-plus 完整 CRUD 示例
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-22 16:31
 * @description
 */
@SpringBootTest
public class CrudTest {

    @Autowired
    private UserMapper userMapper;

    /* 新增操作 */
    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("石原里美"); // 注意此属性与数据库表字段名称不一致
        user.setAge(23);
        user.setEmail("aaaa@moon.com");
        user.setAddress("101号");

        // 返回的result是受影响的行数，并不是自增后的id
        int result = userMapper.insert(user);
        System.out.println("影响行数result：" + result);
        assertThat(result).isGreaterThan(0);
        // 成功插入数据后，返回自增的ID
        Long id = user.getId();
        System.out.println("返回的自增ID" + id);
        assertThat(id).isNotNull();
    }

    /* 更新操作 */
    @Test
    public void testUpdate() {
        /* 1. 根据 ID 修改 */
        User u1 = new User();
        u1.setId(5L);
        u1.setUsername("新垣结衣");

        // 返回的result是受影响的行数
        int result = userMapper.updateById(u1);
        System.out.println("影响行数result：" + result);
        assertThat(result).isGreaterThan(0);

        /* 2. 根据 whereWrapper 条件，更新记录 */
        // 方式1: 通过QueryWrapper对象更新
        User u2 = new User();
        u2.setEmail("mk@moon.com");
        int result2 = userMapper.update(u2, new QueryWrapper<User>().lambda().eq(User::getId, 4L));
        System.out.println("影响行数result2：" + result2);
        assertThat(result2).isGreaterThan(0);

        // 方式2：通过LambdaUpdateWrapper<T>对象更新
        int result3 = userMapper.update(null, Wrappers.<User>lambdaUpdate().set(User::getEmail, null).eq(User::getId, 3L));
        System.out.println("影响行数result3：" + result3);
        assertThat(result3).isGreaterThan(0);
    }


}
