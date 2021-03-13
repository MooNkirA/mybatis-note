package com.moon.mybatis.test;

import com.moon.mybatis.dao.Anno_CommonMapper;
import com.moon.mybatis.dao.CommonMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * 直接基于 Java 代码而不是 XML 文件中创建配置，或者想要创建你自己的配置建造器的测试
 * TODO: 待完善
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-12 11:05
 * @description
 */
public class MyBatisNoXmlTest {

    @Test
    public void testMyBatis() throws IOException {
        // 官网上的demo，但源码中没有找到BlogDataSourceFactory这个对象？？
        // DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();

        // 读取数据库配置文件，文件中直接配置了url、username、password、driver等参数值，key值与创建数据源的一致
        Properties properties = Resources.getResourceAsProperties("mysql.properties");
        // 创建数据源
        PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
        pooledDataSourceFactory.setProperties(properties);
        DataSource dataSource = pooledDataSourceFactory.getDataSource();

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        // configuration 添加了一个映射器类（mapper class）。映射器类是 Java 类，它们包含 SQL 映射注解从而避免依赖 XML 文件。
        configuration.addMapper(Anno_CommonMapper.class);
        // 创建连接工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        // 开启连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取需要调用的mapper接口
        CommonMapper mapper = sqlSession.getMapper(CommonMapper.class);
        System.out.println(mapper.queryAreaByAreaCode(new HashMap()));
    }

}
