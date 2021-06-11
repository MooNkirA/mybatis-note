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
 * 测试 Batis 示例使用
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-10 23:11
 * @description
 */
public class MyBatisBasicTest {

    private final String RESOURCE = "mybatis-config.xml";

    @Test
    public void testMyBatisSelectList() {
        try {
            // ----------------------------------------- 第一阶段 -----------------------------------------
            // 从 XML 文件中构建 SqlSessionFactory 的实例非常简单，建议使用类路径下的资源文件进行配置。
            // MyBatis 包含一个名叫 Resources 的工具类，它包含一些实用方法，可以使类路径或其它位置加载资源文件
            InputStream inputStream = Resources.getResourceAsStream(RESOURCE);
            // 读取配置文件，创建SqlSessionFactory
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            // ----------------------------------------- 第二阶段 -----------------------------------------
            // 通过SqlSessionFactory开启一个SqlSession
            SqlSession sqlSession = sqlSessionFactory.openSession();

            // ----------------------------------------- 第三阶段 -----------------------------------------
            // 通过SqlSession调用相应的数据库操作方法
            System.out.println(sqlSession.selectList("com.moon.mybatis.dao.UserMapper.queryAllUser", null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMyBatisGetMapper() {
        try {
            // ----------------------------------------- 第一阶段 -----------------------------------------
            // 从 XML 文件中构建 SqlSessionFactory 的实例非常简单，建议使用类路径下的资源文件进行配置。
            // MyBatis 包含一个名叫 Resources 的工具类，它包含一些实用方法，可以使类路径或其它位置加载资源文件
            InputStream inputStream = Resources.getResourceAsStream(RESOURCE);
            // 读取配置文件，创建SqlSessionFactory
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            // ----------------------------------------- 第二阶段 -----------------------------------------
            // 通过SqlSessionFactory开启一个SqlSession
            SqlSession sqlSession = sqlSessionFactory.openSession();
            // 通过SqlSession获取指定的mapper映射器（其实是Mapper接口的代理）
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            // ----------------------------------------- 第三阶段 -----------------------------------------
            // 通过代理实例调用相应Mapper接口中的方法
            System.out.println(mapper.queryAllUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
