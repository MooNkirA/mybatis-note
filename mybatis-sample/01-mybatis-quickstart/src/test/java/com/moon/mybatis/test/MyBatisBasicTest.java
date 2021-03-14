package com.moon.mybatis.test;

import com.moon.mybatis.dao.CommonMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 测试 Batis 示例使用
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-10 23:11
 * @description
 */
public class MyBatisBasicTest {

    private InputStream inputStream = null;

    @Before
    public void init() {
        try {
            // 从 XML 文件中构建 SqlSessionFactory 的实例非常简单，建议使用类路径下的资源文件进行配置。
            String resource = "mybatis-config.xml";
            // MyBatis 包含一个名叫 Resources 的工具类，它包含一些实用方法，可以使类路径或其它位置加载资源文件
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMyBatisSelectList() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println(sqlSession.selectList("com.moon.mybatis.dao.CommonMapper.queryAreaByAreaCode", new HashMap<>()));
    }

    @Test
    public void testMyBatisGetMapper() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CommonMapper mapper = sqlSession.getMapper(CommonMapper.class);
        System.out.println(mapper.queryAreaByAreaCode(new HashMap<>()));
    }

}
