package com.moon.mybatisplus.samples.wrapper.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moon.mybatisplus.samples.wrapper.entity.User;
import com.moon.mybatisplus.samples.wrapper.mapper.RoleMapper;
import com.moon.mybatisplus.samples.wrapper.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QueryWrapper 条件构造器测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-26 21:19
 * @description
 */
public class QueryWrapperTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 测试 allEq
     */
    @Test
    public void testAllEq() {
        // 创建QueryWrapper条件查询器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 设置查询条件
        Map<String, Object> params = new HashMap<>();
        params.put("name", "石原里美");
        params.put("age", "24");
        params.put("email", null);

        // allEq(Map<R, V> params)
        // wrapper.allEq(params); // 相当于 name = ? AND age = ? AND email IS NULL

        // allEq(Map<R, V> params, boolean null2IsNull)
        // wrapper.allEq(params, false); // 相当于 name = ? AND age = ?

        // allEq(boolean condition, Map<R, V> params, boolean null2IsNull)
        // wrapper.allEq(false, params, false); // condition设置为false，代表条件都不加入最后生成的sql中，相当于无查询条件

        // allEq(BiPredicate<R, V> filter, Map<R, V> params)
        // allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
        // allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
        wrapper.allEq((k, v) -> k.equals("age"), params); // 过滤key为age的查询条件，相当于 age = ?

        forEachPrint(userMapper.selectList(wrapper));
    }

    /**
     * 测试基础比较操作
     */
    @Test
    public void testBasicQuery() {
        // 等于 =
        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("age", 24));
        forEachPrint(users);

        // 不等于 <>
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().ne("name", "石原里美"));
        forEachPrint(users1);

        // 大于 >
        List<User> users2 = userMapper.selectList(new QueryWrapper<User>().gt("age", 21));
        forEachPrint(users2);

        // 大于等于 >=
        List<User> users3 = userMapper.selectList(new QueryWrapper<User>().ge("age", 21));
        forEachPrint(users3);

        // 小于 <
        List<User> users4 = userMapper.selectList(new QueryWrapper<User>().lt("age", 21));
        forEachPrint(users4);

        // 小于等于 <=
        List<User> users5 = userMapper.selectList(new QueryWrapper<User>().le("age", 21));
        forEachPrint(users5);

        // BETWEEN 值1 AND 值2
        List<User> users6 = userMapper.selectList(new QueryWrapper<User>().between("age", 20, 28));
        forEachPrint(users6);

        // NOT BETWEEN 值1 AND 值2
        List<User> users7 = userMapper.selectList(new QueryWrapper<User>().notBetween("age", 20, 28));
        forEachPrint(users7);

        // IN (值1, 值2, ...)
        List<User> users8 = userMapper.selectList(new QueryWrapper<User>().in("age", 20, 24));
        forEachPrint(users8);

        // NOT IN (值1, 值2, ...)
        List<User> users9 = userMapper.selectList(new QueryWrapper<User>().notIn("age", 20, 24));
        forEachPrint(users9);

        // 多个基本操作符连接使用
        List<User> users10 = userMapper.selectList(new QueryWrapper<User>()
                .between("role_id", 1, 4)
                .gt("age", 20)
                .ne("name", "Tom")); // sql: SELECT * FROM user WHERE (role_id BETWEEN 1 AND 4 AND age > 20 AND name <> 'Tom'
        forEachPrint(users10);
    }

    /**
     * 测试模糊查询
     */
    @Test
    public void testLikeQuery() {
        // LIKE '%值%'
        List<User> users = userMapper.selectList(new QueryWrapper<User>().like("name", "a"));
        forEachPrint(users);

        // NOT LIKE '%值%'
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().notLike("name", "o"));
        forEachPrint(users1);

        // LIKE '%值'
        List<User> users2 = userMapper.selectList(new QueryWrapper<User>().likeLeft("name", "e"));
        forEachPrint(users2);

        // LIKE '值%'
        List<User> users3 = userMapper.selectList(new QueryWrapper<User>().likeRight("name", "J"));
        forEachPrint(users3);
    }

    /**
     * 测试排序查询
     */
    @Test
    public void testOrderQuery() {
        // ORDER BY 字段, ...
        List<User> users = userMapper.selectList(new QueryWrapper<User>().orderBy(true, false, Arrays.asList("name", "age")));
        forEachPrint(users);

        // ORDER BY 字段, ... ASC
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().orderByAsc(Arrays.asList("age", "name")));
        forEachPrint(users1);

        // ORDER BY 字段, ... DESC
        List<User> users2 = userMapper.selectList(new QueryWrapper<User>().orderByDesc(Arrays.asList("name", "age")));
        forEachPrint(users2);
    }

    /**
     * 测试 OR 查询
     */
    @Test
    public void testOrQuery() {
        // 拼接 OR
        // SELECT * FROM user WHERE (name = 'Sandy' OR age = 24)
        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("name", "Sandy").or().eq("age", 24));
        forEachPrint(users);

        // OR 嵌套
        // SELECT * FROM user WHERE (age > 21 OR (role_id BETWEEN 1 AND 4 AND name = 'Jack'))
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>()
                .gt("age", 21)
                .or(i -> i.between("role_id", 1, 4).eq("name", "Jack")));
        forEachPrint(users1);
    }

    /**
     * 测试 AND 查询
     */
    @Test
    public void testAndQuery() {
        // AND 嵌套
        // SELECT * FROM user WHERE (age > 21 AND (role_id BETWEEN 1 AND 4 AND name = 'Tom'))
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .gt("age", 21)
                .and(i -> i.between("role_id", 1, 4).eq("name", "Tom")));
        forEachPrint(users);
    }

    /**
     * 测试 QueryWrapper 的 select 查询
     */
    @Test
    public void testSelect() {
        // 创建QueryWrapper条件查询器
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        // 设置需查询的字段
        wrapper.select("id", "name").between("age", 20, 29)
                .orderByDesc("age");
        forEachPrint(userMapper.selectList(wrapper));
    }

}
