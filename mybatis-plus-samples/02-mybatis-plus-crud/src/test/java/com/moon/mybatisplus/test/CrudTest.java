package com.moon.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moon.mybatisplus.entity.TableUser;
import com.moon.mybatisplus.entity.User;
import com.moon.mybatisplus.mapper.TableUserMapper;
import com.moon.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private TableUserMapper tableUserMapper;

    /* 新增操作 */
    @Test
    public void testInsert() {
        User user = new User();
        user.setName("石原里美");
        user.setAge(24);
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

    /* 根据 ID 更新数据 */
    @Test
    public void testUpdateById() {
        User u1 = new User();
        u1.setId(5L);
        u1.setName("新垣结衣");

        // 返回的result是受影响的行数
        int result = userMapper.updateById(u1);
        System.out.println("根据id更新影响行数：" + result);
    }

    /* 根据 Wrapper 条件更新数据 */
    @Test
    public void testUpdateByWrapper() {
        // 方式1: 通过QueryWrapper对象更新
        User user = new User();
        user.setEmail("mk@moon.com");
        int result1 = userMapper.update(user, new QueryWrapper<User>().lambda().eq(User::getId, 4L));
        System.out.println("影响行数result1：" + result1);

        // 方式2：通过LambdaUpdateWrapper<T>对象更新
        int result2 = userMapper.update(null, Wrappers.<User>lambdaUpdate().set(User::getEmail, null).eq(User::getId, 3L));
        System.out.println("影响行数result2：" + result2);
    }

    /* 根据ID删除记录 */
    @Test
    public void testDeleteById() {
        // 直接根据id值删除
        int result1 = userMapper.deleteById("1452460873191886851");
        System.out.println("根据id删除影响行数：" + result1);

        // 根据实体对象(ID)主键属性删除
        User user = new User();
        user.setId(1452460873191886852L);
        int result2 = userMapper.deleteById(user);
        System.out.println("根据实体对象(ID)删除影响行数：" + result2);
    }

    /* 根据 columnMap 条件删除记录 */
    @Test
    public void testDeleteByMap() {
        // 创建 map 条件
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("age", 24);
        columnMap.put("name", "石原里美");

        // 根据 columnMap 的元素设置为删除的条件，多个值之间为and关系
        int result = userMapper.deleteByMap(columnMap);
        System.out.println("根据 Map 条件删除影响行数：" + result);
    }

    /* 根据 Wrapper 条件删除记录 */
    @Test
    public void testDeleteByWrapper() {
        // 创建实体对象查询条件
        User user = new User();
        user.setAge(24);
        user.setName("石原里美");

        // 将实体对象进行包装，包装为查询操作条件
        Wrapper<User> wrapper = new QueryWrapper<>(user);
        int result = userMapper.delete(wrapper);
        System.out.println("根据 Wrapper 对象条件删除影响行数：" + result);
    }

    /* 根据 ID 批量删除记录 */
    @Test
    public void testDeleteBatchIds() {
        // 创建ID集合批量删除
        int result = userMapper.deleteBatchIds(Arrays.asList(1L, 2L, 4L, 5L));
        System.out.println("根据id批量删除影响行数：" + result);
    }

    /* 根据主键 ID 查询记录 */
    @Test
    public void testSelectById() {
        TableUser user = tableUserMapper.selectById(3L);
        System.out.println("根据id查询结果：" + user);
    }

    /* 根据 ID 批量查询记录 */
    @Test
    public void testSelectBatchIds() {
        List<TableUser> users = tableUserMapper.selectBatchIds(Arrays.asList(1L, 2L, 4L, 5L));
        System.out.println("根据id批量查询结果：" + users);
    }

    /* 查询单条记录 */
    @Test
    public void testSelectOne() {
        // 创建查询条件
        QueryWrapper<TableUser> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "Tom");
        // 根据条件查询一条数据，如果结果超过一条会报错
        TableUser user = tableUserMapper.selectOne(wrapper);
        System.out.println("查询结果：" + user);
    }

    /* 条件查询总记录 */
    @Test
    public void testSelectCount() {
        // 创建查询条件
        QueryWrapper<TableUser> wrapper = new QueryWrapper<>();
        // wrapper.gt("age", 21); // 查询年龄大于21
        wrapper.ge("age", 21); // 查询年龄大于等于21
        // 根据条件查询数据条数
        Long count = tableUserMapper.selectCount(wrapper);
        System.out.println("查询总记录数：" + count);
    }

    /* 条件查询记录 */
    @Test
    public void testSelectList() {
        // 方式1：创建 QueryWrapper 查询条件
        QueryWrapper<TableUser> wrapper = new QueryWrapper<>();
        wrapper.ge("age", 21); // 查询年龄大于等于21
        // 根据条件查询数据
        List<TableUser> users = tableUserMapper.selectList(wrapper);
        System.out.println("QueryWrapper 查询结果：" + users);

        // 方式1：创建 lambdaQuery 查询条件
        List<User> userList = userMapper.selectList(Wrappers.<User>lambdaQuery().select(User::getId, User::getName));
        // 上面是查询所有记录，但只返回id与name属性
        System.out.println("LambdaQueryWrapper 查询结果：" + userList);
    }

    /* 条件分页查询记录 */
    @Test
    public void testSelectPage() {
        // 创建 QueryWrapper 查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("age", 21); // 查询年龄大于等于21
        // 创建分页对象
        Page<User> page = new Page<>(1, 2);
        // 根据条件分页查询数据
        IPage<User> iPage = userMapper.selectPage(page, wrapper);
        System.out.println("数据总条数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        List<User> users = iPage.getRecords();
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }

}
