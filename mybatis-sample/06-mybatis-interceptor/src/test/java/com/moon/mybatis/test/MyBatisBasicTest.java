package com.moon.mybatis.test;

import com.moon.mybatis.dao.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 测试 MyBatis 自定义插件
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-10 23:11
 * @description
 */
public class MyBatisBasicTest {

    private final String RESOURCE = "mybatis-config.xml";

    @Test
    public void testMyBatisPlugins() throws IOException {
        // 从 XML 文件中构建 SqlSessionFactory 的实例
        InputStream inputStream = Resources.getResourceAsStream(RESOURCE);
        // 读取配置文件，创建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过SqlSessionFactory开启一个SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 通过SqlSession获取指定的mapper映射器（其实是Mapper接口的代理）
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 通过代理实例调用相应Mapper接口中的方法
        System.out.println(mapper.queryAllUser());
    }

}
