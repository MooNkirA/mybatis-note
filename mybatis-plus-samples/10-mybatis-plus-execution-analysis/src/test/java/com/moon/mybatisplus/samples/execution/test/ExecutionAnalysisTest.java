package com.moon.mybatisplus.samples.execution.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.moon.mybatisplus.samples.execution.entity.User;
import com.moon.mybatisplus.samples.execution.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 执行SQL分析插件示例
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 15:24
 * @description
 */
@Slf4j
@SpringBootTest
public class ExecutionAnalysisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testExecutionAnalysis() {
        // 查询全部
        userMapper.selectList(new QueryWrapper<>());
        // 删除
        userMapper.deleteById(7L);

        User user = new User();
        user.setId(7L);
        user.setName("testAnalysis");
        user.setEmail("testAnalysis@moon.com");
        user.setAge(11);
        user.setRoleId(1L);

        userMapper.insert(user);
        userMapper.update(user, new QueryWrapper<User>().eq("id", 7L));
        try {
            userMapper.update(new User(), new QueryWrapper<>());
        } catch (MyBatisSystemException e) {
        }
        try {
            // 执行分析插件 BlockAttackInnerInterceptor
            userMapper.delete(new QueryWrapper<>());
        } catch (MyBatisSystemException e) {
            System.err.println("执行了全表删除拦截，删除无效！异常：" + e.getMessage());
        }
        Assertions.assertTrue(CollectionUtils.isNotEmpty(userMapper.selectList(new QueryWrapper<>())), "数据都被删掉了.(┬＿┬)");
    }

}
