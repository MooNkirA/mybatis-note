package com.moon.mybatisplus.samples.ar.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-Plus 配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 09:36
 * @description
 */
@Configuration
@MapperScan("com.moon.mybatisplus.samples.ar.mapper")
public class MybatisPlusConfig {
}
