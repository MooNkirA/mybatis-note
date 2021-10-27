package com.moon.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Wrapper 条件构造器应用
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-26 21:16
 * @description
 */
@SpringBootApplication
@MapperScan("com.moon.mybatisplus.mapper")
public class WrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(WrapperApplication.class, args);
    }

}
