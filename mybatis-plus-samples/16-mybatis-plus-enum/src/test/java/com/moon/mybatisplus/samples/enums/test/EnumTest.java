package com.moon.mybatisplus.samples.enums.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moon.mybatisplus.samples.enums.entity.User;
import com.moon.mybatisplus.samples.enums.enums.AgeEnum;
import com.moon.mybatisplus.samples.enums.enums.RoleEnum;
import com.moon.mybatisplus.samples.enums.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 通用枚举测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-12 9:50
 * @description
 */
@SpringBootTest
public class EnumTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试插入数据时，通用枚举进行替换。
     * 查询数据时，根据值转换成相应枚举。
     */
    @Test
    public void testInsert() {
        User user = new User();
        user.setName("我是N");
        user.setAge(AgeEnum.EIGHTTEEN);
        user.setRoleId(RoleEnum.PROGRAMMER);

        /*
         * 测试新增，相应的枚举会替换成相应的数值。示例sql如下：
         *   Preparing: INSERT INTO user ( name, age, role_id ) VALUES ( ?, ?, ? )
         *   Parameters: 我是N(String), 18(Integer), 3(Long)
         */
        userMapper.insert(user);
        // 获取新增的记录id，查询刚刚新增的记录
        User userDb = userMapper.selectById(user.getId());
        // 查询时 MP 也会根据数据值替换成相应的枚举
        System.out.println("新增的用户记录：" + userDb);
    }

    /**
     * 测试查询时，查询条件的通用枚举转换
     */
    @Test
    public void testQueryWrapper() {
        // 创建查询条件
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>()
                .lambda()
                .ge(User::getAge, AgeEnum.EIGHTTEEN)
                .eq(User::getRoleId, RoleEnum.CONSUMER);

        /*
         * 测试查询，查询条件会替换成相应的枚举值。示例sql如下：
         *   Preparing: SELECT id,name,age,email,role_id FROM user WHERE (age >= ? AND role_id = ?)
         *   Parameters: 18(Integer), 2(Long)
         */
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

}
