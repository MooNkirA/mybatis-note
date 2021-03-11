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
            String resource = "mybatis-config.xml";
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
        System.out.println(mapper.queryAreaByAreaCode(new HashMap()));
    }

}
