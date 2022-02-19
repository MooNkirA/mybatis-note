package com.moon.mybatisplus.samples.servicecrud.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
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
@MapperScan("com.moon.mybatisplus.samples.servicecrud.mapper")
public class MybatisPlusConfig {

    /**
     * 创建MyBatis-Plus的分页插件，注册到spring ioc容器中 （最新版）
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
