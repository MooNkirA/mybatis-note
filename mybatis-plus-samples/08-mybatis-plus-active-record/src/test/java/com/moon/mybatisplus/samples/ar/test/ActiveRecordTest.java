package com.moon.mybatisplus.samples.ar.test;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.moon.mybatisplus.samples.ar.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * ActiveRecord 使用示例
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 9:53
 * @description
 */
@SpringBootTest
public class ActiveRecordTest {

    /**
     * ActiveRecord 新增
     */
    @Test
    public void testActiveRecordInsert() {
        User user = new User();
        user.setName("sunny")
                .setAge(25)
                .setEmail("miemie@moon.com")
                .setRoleId(1L);

        // 测试使用 ActiveRecord 新增数据
        Assertions.assertTrue(user.insert());
        // 新增成功后，可以直接成实体对象中获取主键 ID
        System.err.println("\n插入成功 ID 为：" + user.getId());
    }

    /**
     * ActiveRecord 根据 id 删除
     */
    @Test
    public void testActiveRecordDeleteById() {
        User user = new User();
        user.setId(7L);

        // 测试使用 ActiveRecord 根据id删除数据
        Assertions.assertTrue(user.deleteById());
    }

    /**
     * ActiveRecord 条件删除
     */
    @Test
    public void testActiveRecordDeleteByWrapper() {
        // 创建查询条件
        Wrapper<User> wrapper = new QueryWrapper<User>()
                .lambda()
                .eq(User::getName, "sunny");

        // 测试使用 ActiveRecord 根据条件删除数据
        Assertions.assertTrue(new User().delete(wrapper));
    }

    /**
     * ActiveRecord 根据 id 更新
     */
    @Test
    public void testActiveRecordUpdateById() {
        User user = new User();
        user.setId(11L).setName("长泽雅美").setRoleId(2L);

        // 测试使用 ActiveRecord 根据id更新数据
        Assertions.assertTrue(user.updateById());
    }

    /**
     * ActiveRecord 条件更新
     */
    @Test
    public void testActiveRecordUpdateByWrapper() {
        // 创建查询条件
        Wrapper<User> wrapper = new UpdateWrapper<User>()
                .lambda()
                .set(User::getAge, 18)
                .eq(User::getId, 11L);

        // 测试使用 ActiveRecord 根据条件删除数据
        Assertions.assertTrue(new User().update(wrapper));
    }

    /**
     * ActiveRecord 查询
     */
    @Test
    public void testActiveRecordQuery() {
        /* 根据id查询 */
        User user = new User().setId(11L);
        System.out.println(user.selectById());

        /* 根据条件查询 */
        Wrapper<User> wrapper = new QueryWrapper<User>().lambda().gt(User::getAge, 20);
        List<User> users = user.selectList(wrapper);
        users.forEach(System.out::println);
    }
}
