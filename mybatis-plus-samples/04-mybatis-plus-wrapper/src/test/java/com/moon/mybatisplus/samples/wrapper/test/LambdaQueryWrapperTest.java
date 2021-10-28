package com.moon.mybatisplus.samples.wrapper.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
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
 * LambdaQueryWrapper 查询器测试（示例与QueryWrapper一样）
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-27 15:25
 * @description
 */
public class LambdaQueryWrapperTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 测试 allEq
     */
    @Test
    public void testAllEq() {
        // 创建LambdaQueryWrapper条件查询器
        LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
        // 设置查询条件
        Map<SFunction<User, ?>, Object> params = new HashMap<>();
        params.put(User::getName, "石原里美");
        params.put(User::getAge, "24");
        params.put(User::getEmail, null);

        // allEq(Map<R, V> params)
        // lambda.allEq(params); // 相当于 name = ? AND age = ? AND email IS NULL

        // allEq(Map<R, V> params, boolean null2IsNull)
        // lambda.allEq(params, false); // 相当于 name = ? AND age = ?

        // allEq(boolean condition, Map<R, V> params, boolean null2IsNull)
        // lambda.allEq(false, params, false); // condition设置为false，代表条件都不加入最后生成的sql中，相当于无查询条件

        // allEq(BiPredicate<R, V> filter, Map<R, V> params)
        // allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
        // allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
        lambda.allEq((k, v) -> true, params); // 过滤key为age的查询条件，相当于 age = ?

        forEachPrint(userMapper.selectList(lambda));
    }

    /**
     * 测试基础比较操作
     */
    @Test
    public void testBasicQuery() {
        // 等于 =
        List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getAge, 24));
        forEachPrint(users);

        // 不等于 <>
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().lambda().ne(User::getName, "石原里美"));
        forEachPrint(users1);

        // 大于 >
        List<User> users2 = userMapper.selectList(new QueryWrapper<User>().lambda().gt(User::getAge, 21));
        forEachPrint(users2);

        // 大于等于 >=
        List<User> users3 = userMapper.selectList(new QueryWrapper<User>().lambda().ge(User::getAge, 21));
        forEachPrint(users3);

        // 小于 <
        List<User> users4 = userMapper.selectList(new QueryWrapper<User>().lambda().lt(User::getAge, 21));
        forEachPrint(users4);

        // 小于等于 <=
        List<User> users5 = userMapper.selectList(new QueryWrapper<User>().lambda().le(User::getAge, 21));
        forEachPrint(users5);

        // BETWEEN 值1 AND 值2
        List<User> users6 = userMapper.selectList(new QueryWrapper<User>().lambda().between(User::getAge, 20, 28));
        forEachPrint(users6);

        // NOT BETWEEN 值1 AND 值2
        List<User> users7 = userMapper.selectList(new QueryWrapper<User>().lambda().notBetween(User::getAge, 20, 28));
        forEachPrint(users7);

        // IN (值1, 值2, ...)
        List<User> users8 = userMapper.selectList(new QueryWrapper<User>().lambda().in(User::getAge, 20, 24));
        forEachPrint(users8);

        // NOT IN (值1, 值2, ...)
        List<User> users9 = userMapper.selectList(new QueryWrapper<User>().lambda().notIn(User::getAge, 20, 24));
        forEachPrint(users9);

        // 多个基本操作符连接使用
        List<User> users10 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .between(User::getRoleId, 1, 4)
                .gt(User::getAge, 20)
                .ne(User::getName, "Tom")); // sql: SELECT * FROM user WHERE (role_id BETWEEN 1 AND 4 AND age > 20 AND name <> 'Tom'
        forEachPrint(users10);
    }

    /**
     * 测试模糊查询
     */
    @Test
    public void testLikeQuery() {
        // LIKE '%值%'
        List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda().like(User::getName, "a"));
        forEachPrint(users);

        // NOT LIKE '%值%'
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().lambda().notLike(User::getName, "o"));
        forEachPrint(users1);

        // LIKE '%值'
        List<User> users2 = userMapper.selectList(new QueryWrapper<User>().lambda().likeLeft(User::getName, "e"));
        forEachPrint(users2);

        // LIKE '值%'
        List<User> users3 = userMapper.selectList(new QueryWrapper<User>().lambda().likeRight(User::getName, "J"));
        forEachPrint(users3);
    }

    /**
     * 测试排序查询
     */
    @Test
    public void testOrderQuery() {
        // ORDER BY 字段, ...
        List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda()
                .orderBy(true, false, Arrays.asList(User::getName, User::getAge)));
        forEachPrint(users);

        // ORDER BY 字段, ... ASC
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .orderByAsc(Arrays.asList(User::getAge, User::getName)));
        forEachPrint(users1);

        // ORDER BY 字段, ... DESC
        List<User> users2 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .orderByDesc(Arrays.asList(User::getName, User::getAge)));
        forEachPrint(users2);
    }

    /**
     * 测试 OR 查询
     */
    @Test
    public void testOrQuery() {
        // 拼接 OR
        // SELECT * FROM user WHERE (name = 'Sandy' OR age = 24)
        List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda()
                .eq(User::getName, "Sandy").or().eq(User::getAge, 24));
        forEachPrint(users);

        // OR 嵌套
        // SELECT * FROM user WHERE (age > 21 OR (role_id BETWEEN 1 AND 4 AND name = 'Jack'))
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().lambda()
                .gt(User::getAge, 21)
                .or(i -> i.between(User::getRoleId, 1, 4).eq(User::getName, "Jack")));
        forEachPrint(users1);
    }

    /**
     * 测试 AND 查询
     */
    @Test
    public void testAndQuery() {
        // AND 嵌套
        // SELECT * FROM user WHERE (age > 21 AND (role_id BETWEEN 1 AND 4 AND name = 'Tom'))
        List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda()
                .gt(User::getAge, 21)
                .and(i -> i.between(User::getRoleId, 1, 4).eq(User::getName, "Tom")));
        forEachPrint(users);
    }

    /**
     * 测试 QueryWrapper 的 select 查询
     */
    @Test
    public void testSelect() {
        // 创建QueryWrapper条件查询器
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 设置需查询的字段
        lambdaQueryWrapper.select(User::getRoleId, User::getName)
                .between(User::getAge, 20, 29)
                .orderByDesc(User::getAge);
        forEachPrint(userMapper.selectList(lambdaQueryWrapper));
    }

}
