package com.moon.mybatisplus.samples.sequence.config;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MP 配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 11:38
 * @description
 */
@Configuration
@MapperScan("com.moon.mybatisplus.samples.sequence.mapper")
public class MybatisPlusConfig {

    /**
     * sequence主键，需要配置一个主键生成器
     * 配合实体类注解 {@link KeySequence} + {@link TableId} type=INPUT
     *
     * @return
     */
    @Bean
    public OracleKeyGenerator oracleKeyGenerator() {
        return new OracleKeyGenerator();
    }

}
