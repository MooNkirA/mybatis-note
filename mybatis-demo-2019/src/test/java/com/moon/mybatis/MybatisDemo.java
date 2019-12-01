package com.moon.mybatis;

import com.moon.mybatis.entity.TUser;
import com.moon.mybatis.mapper.TUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Mybatis 基本使用demo
 *
 * @author MoonZero
 * @version 1.0
 * @date 2019-11-30 18:30
 * @description
 */
public class MybatisDemo {

    /* 定义Sqlsession工厂类 */
    private SqlSessionFactory sqlSessionFactory;

    private static final String RESOURCE = "mybatis-config.xml";

    @Before
    public void init() throws IOException {
        /*--------------------第一阶段---------------------------*/
        // 1.1 读取mybatis配置文件创建SqlSessionFactory
        InputStream inputStream = Resources.getResourceAsStream(RESOURCE);
        // 1.2 读取mybatis配置文件创SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }

    // 快速入门
    @Test
    public void quickStart() {
        /*--------------------第二阶段---------------------------*/
        // 2. 获取sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3. 获取对应mapper
        TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);

        /*--------------------第三阶段---------------------------*/
        // 4.1 执行查询语句并返回单条数据
        TUser user = mapper.selectByPrimaryKey(203);
        System.out.println(user);
        System.out.println("----------------------------------");

        // 4.2 执行查询语句并返回多条数据
        List<TUser> users = mapper.selectAll();
        for (TUser u : users) {
            System.out.println(u);
        }
    }

}
