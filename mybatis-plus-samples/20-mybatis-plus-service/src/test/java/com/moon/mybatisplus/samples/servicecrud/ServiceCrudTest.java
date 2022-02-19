package com.moon.mybatisplus.samples.servicecrud;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moon.mybatisplus.samples.servicecrud.entity.User;
import com.moon.mybatisplus.samples.servicecrud.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用 Service CRUD 封装使用测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-02-19 11:36
 * @description
 */
@SpringBootTest
public class ServiceCrudTest {

    @Autowired
    private IUserService userService;

    /* 新增操作 */
    @Test
    public void testSave() {
        User user = new User();
        user.setName("石原里美");
        user.setAge(24);
        user.setEmail("aaaa@moon.com");
        user.setRoleId(1L);

        // 方法返回是否成功布尔值
        boolean result = userService.save(user);
        System.out.println("是否新增成功：" + result);
    }

    /* 批量新增操作 */
    @Test
    public void testSaveBatch() {
        List<User> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            User user = new User();
            user.setName("石原里美" + i);
            user.setAge(24 + i);
            user.setEmail("aaaa@moon.com");
            user.setRoleId(1L);
            list.add(user);
        }

        // 方法返回是否成功布尔值
        boolean result = userService.saveBatch(list);
        System.out.println("是否批量新增成功：" + result);
    }

    /* 新增或者更新操作 */
    @Test
    public void testSaveOrUpdate() {
        User user = new User();
        user.setId(101L);
        user.setName("天锁斩月");
        user.setAge(25);
        user.setEmail("moon@moon.com");
        user.setRoleId(1L);

        // 方法返回是否成功布尔值
        boolean result = userService.saveOrUpdate(user);
        System.out.println("是否新增/更新成功：" + result);
    }

    /* 根据id更新操作 */
    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(20L);
        user.setName("香风智乃");
        user.setRoleId(3L);

        // 方法返回是否成功布尔值
        boolean result = userService.updateById(user);
        System.out.println("是否更新成功：" + result);
    }

    /* 根据 id 删除操作 */
    @Test
    public void testRemoveById() {
        User user = new User();
        user.setId(13L);

        // 方法返回是否成功布尔值
        boolean result = userService.removeById(user);
        System.out.println("是否删除成功：" + result);
    }

    /* 根据条件删除操作 */
    @Test
    public void testRemove() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("age", 27);

        // 方法返回是否成功布尔值
        boolean result = userService.remove(wrapper);
        System.out.println("是否删除成功：" + result);
    }

    /* 查询单个操作 */
    @Test
    public void testGetById() {
        User user = new User();
        user.setId(11L);

        // 方法返回查询对象
        User result = userService.getById(user);
        System.out.println(result);
    }

    /* 根据条件查询单个操作 */
    @Test
    public void testGetOne() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 102L);

        // 查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
        User result = userService.getOne(wrapper);
        System.out.println(result);
    }

    /* 根据条件查询多个操作 */
    @Test
    public void testList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.le("age", 23);

        // 返回多条记录
        List<User> list = userService.list(wrapper);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }

    /* 根据条件分页查询多个操作 */
    @Test
    public void testPage() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.le("age", 23);

        Page<User> page = new Page<>(1, 2);

        // 返回分页多条记录
        IPage<User> list = userService.page(page, wrapper);
        if (!CollectionUtils.isEmpty(list.getRecords())) {
            list.getRecords().forEach(System.out::println);
        }
    }

    /* 聚合统计操作 */
    @Test
    public void testCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.le("age", 23);

        // 返回记录数
        long count = userService.count(wrapper);
        System.out.println("记录数：" + count);
    }

    /* 链式查询操作 */
    @Test
    public void testQuery() {
        List<User> list = userService.query().le("age", 24).list();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }

}
