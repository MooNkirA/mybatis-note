package com.moon.mybatis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring 配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-06-12 11:41
 * @description
 */
@Configuration
@Import({MyBatisConfig.class, DruidConfig.class}) // 导入MyBatis的配置类、Druid数据源配置类（基于@PropertySource注解读取properties配置）
// @Import({MyBatisConfig.class, DruidConfig2.class}) // 导入MyBatis的配置类、Druid数据源配置类(基于自定义工具类读取properties配置)
@ComponentScan("com.moon.mybatis") // 配置spring的包扫描
// @EnableTransactionManagement // 配置是否开启事务管理器
@PropertySource("classpath:log4j.properties") // 引入日志配置文件
public class SpringConfig {
}
