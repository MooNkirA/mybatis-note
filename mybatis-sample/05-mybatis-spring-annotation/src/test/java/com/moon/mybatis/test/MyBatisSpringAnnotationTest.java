package com.moon.mybatis.test;

import com.moon.mybatis.config.SpringConfig;
import com.moon.mybatis.dao.UserMapper;
import com.moon.mybatis.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * MyBatis 与 Spring 基于纯注解方式整合测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-06-12 12:06
 * @description
 */
public class MyBatisSpringAnnotationTest {

    @Test
    public void testMyBatisSpringAnnoBasic() {
        // 1. 基于xml配置，创建spring容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        // 2. 从spring容器中，获取SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        // 3. 通过SqlSessionFactory开启一个SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4. 通过SqlSession获取指定的mapper映射器
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 5. 通过代理实例调用相应Mapper接口中的方法
        List<User> users = mapper.queryAllUser();
        System.out.println(users);
    }

}
