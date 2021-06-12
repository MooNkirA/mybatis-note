package com.moon.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * Druid 数据源配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-06-12 15:19
 * @description
 */
@PropertySource("classpath:db.properties") // 获取配置文件
public class DruidConfig {

    @Value("${mybatis.url}")
    private String jdbcUrl;
    @Value("${mybatis.driverClassName}")
    private String driverClassName;
    @Value("${mybatis.username}")
    private String username;
    @Value("${mybatis.password}")
    private String password;

    @Value("${mybatis.druid.initialSize}")
    private int initialSize;
    @Value("${mybatis.druid.minIdle}")
    private int minIdle;
    @Value("${mybatis.druid.maxActive}")
    private int maxActive;
    @Value("${mybatis.druid.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${mybatis.druid.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;
    @Value("${mybatis.druid.validationQuery}")
    private String validationQuery;
    @Value("${mybatis.druid.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${mybatis.druid.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${mybatis.druid.testOnReturn}")
    private boolean testOnReturn;
    @Value("${mybatis.druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${mybatis.druid.maxPoolPreparedStatementPerConnectionSize}")
    private Integer maxPoolPreparedStatementPerConnectionSize;
    @Value("${mybatis.druid.filters}")
    private String filters;
    @Value("${mybatis.druid.connectionProperties}")
    private String connectionProperties;

    @Bean("dataSource")
    public DataSource createDataSource() {
        // 1. 创建Druid数据源
        DruidDataSource druidDataSource = new DruidDataSource();
        // 2. 配置数据源相关参数
        druidDataSource.setDriverClassName(this.driverClassName);
        druidDataSource.setUrl(this.jdbcUrl);
        druidDataSource.setUsername(this.username);
        druidDataSource.setPassword(this.password);
        druidDataSource.setMaxActive(this.maxActive);
        druidDataSource.setInitialSize(this.initialSize);
        druidDataSource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(this.validationQuery);
        druidDataSource.setTestWhileIdle(this.testWhileIdle);
        druidDataSource.setTestOnBorrow(this.testOnBorrow);
        druidDataSource.setTestOnReturn(this.testOnReturn);
        druidDataSource.setPoolPreparedStatements(this.poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize);

        return druidDataSource;
    }

}
