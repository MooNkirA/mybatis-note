package com.moon.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MyBatis-Plus 快速入门示例
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-19 22:47
 * @description
 */
@SpringBootApplication
@MapperScan("com.moon.mybatisplus.mapper") // 设置mapper接口的扫描包
public class QuickstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickstartApplication.class, args);
    }

}
