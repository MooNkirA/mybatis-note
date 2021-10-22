package com.moon.mybatisplus.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-Plus 配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-22 15:35
 * @description
 */
@Configuration
@MapperScan("com.moon.mybatisplus.mapper")
public class MybatisPlusConfig {
}
