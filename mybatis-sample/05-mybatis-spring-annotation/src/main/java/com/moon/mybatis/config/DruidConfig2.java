package com.moon.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.moon.mybatis.utils.PropertiesReader;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Druid 数据源配置类（使用自定义properties读取工具类）
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-06-12 15:19
 * @description
 */
public class DruidConfig2 {

    @Bean("dataSource")
    public DataSource createDataSource() {
        // 1. 创建Druid数据源
        DruidDataSource druidDataSource = new DruidDataSource();
        // 2. 配置数据源相关参数
        druidDataSource.setDriverClassName(PropertiesReader.get("mybatis.driverClassName"));
        druidDataSource.setUrl(PropertiesReader.get("mybatis.url"));
        druidDataSource.setUsername(PropertiesReader.get("mybatis.username"));
        druidDataSource.setPassword(PropertiesReader.get("mybatis.password"));
        druidDataSource.setMaxActive(PropertiesReader.getInteger("mybatis.druid.maxActive"));
        druidDataSource.setInitialSize(PropertiesReader.getInteger("mybatis.druid.initialSize"));
        druidDataSource.setMinEvictableIdleTimeMillis(PropertiesReader.getInteger("mybatis.druid.minEvictableIdleTimeMillis"));
        druidDataSource.setValidationQuery(PropertiesReader.get("mybatis.druid.validationQuery"));
        druidDataSource.setTestWhileIdle(PropertiesReader.getBoolean("mybatis.druid.testWhileIdle"));
        druidDataSource.setTestOnBorrow(PropertiesReader.getBoolean("mybatis.druid.testOnBorrow"));
        druidDataSource.setTestOnReturn(PropertiesReader.getBoolean("mybatis.druid.testOnReturn"));
        druidDataSource.setPoolPreparedStatements(PropertiesReader.getBoolean("mybatis.druid.poolPreparedStatements"));
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(PropertiesReader.getInteger("mybatis.druid.maxPoolPreparedStatementPerConnectionSize"));
        try {
            druidDataSource.setFilters(PropertiesReader.get("mybatis.druid.filters"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return druidDataSource;
    }

}
