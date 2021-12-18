package com.moon.mybatisplus.samples.association.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moon.mybatisplus.samples.association.entity.Role;
import com.moon.mybatisplus.samples.association.entity.User;
import com.moon.mybatisplus.samples.association.mapper.RoleMapper;
import com.moon.mybatisplus.samples.association.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 联合查询测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-09 15:26
 * @description
 */
@SpringBootTest
public class AssociationTest {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 测试角色表联合用户表查询
     */
    @Test
    public void testSelectRoleUserResultMap() {
        List<Role> roles = roleMapper.queryAllRole();
        roles.forEach(System.out::println);
    }

    /**
     * 测试角色表联合用户表查询
     */
    @Test
    public void testSelectUserRoleByPage() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("u.id", 3);
        IPage<User> page = new Page<>(1, 2);

        List<User> users = userMapper.queryUserRoleByPage(page, wrapper);
        users.forEach(System.out::println);
    }

    /**
     * TODO: 测试两表联合新增
     */
    @Test
    public void testAssociationInsert() {

    }

    /**
     * TODO: 测试两表联合更新
     */
    @Test
    public void testAssociationUpdate() {

    }

}
