package com.moon.mybatis.config;

import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * MyBatis 总配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-06-12 11:43
 * @description
 */
/*
 * 配置Mapper接口的包扫描，相当于xml方式的配置：
 * <bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
 */
@MapperScan("com.moon.mybatis.dao")
public class MyBatisConfig {

    @Autowired
    private ApplicationContext context;

    /*
     * 配置 SqlSessionFactoryBean，相当于xml方式的配置：
     * <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        // 创建 SqlSessionFactoryBean
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        /* 配置SqlSessionFactoryBean对象相应属性值 */
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);

        // 创建 MyBatis 的“大管家”类
        Configuration configuration = new Configuration();
        // 将手动创建的 Configuration 实例设置到 SqlSessionFactoryBean 对象中
        sqlSessionFactoryBean.setConfiguration(configuration);
        // 设置日志的具体实现
        configuration.setLogImpl(Log4jImpl.class);
        // 设置读取mybatis总配置文件（此示例使用纯注解方式，所以没有此配置文件）
        // sqlSessionFactoryBean.setConfigLocation(context.getResource("classpath:mybatis-config.xml"));
        // 设置读取映射器xml配置文件
        sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath:xml/*Mapper.xml"));
        // 设置别名
        sqlSessionFactoryBean.setTypeAliasesPackage("com.moon.mybatis.pojo");
        // 设置数据库厂商标识
        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("MySQL", "mysql");
        databaseIdProvider.setProperties(properties);
        sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider);
        /* 其他配置也可以按上面的方式进行配置 */

        return sqlSessionFactoryBean;
    }

}
