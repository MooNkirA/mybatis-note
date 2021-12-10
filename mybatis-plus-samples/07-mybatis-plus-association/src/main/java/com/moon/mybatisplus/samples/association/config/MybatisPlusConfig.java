package com.moon.mybatisplus.samples.association.config;

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
 * @date 2021-12-09 14:56
 * @description
 */
@Configuration
@MapperScan("com.moon.mybatisplus.samples.association.mapper")
public class MybatisPlusConfig {

    /**
     * 新的分页插件，一缓和二缓遵循mybatis的规则，需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor mysqlPaginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL); // 此示例使用MySQL数据库
        interceptor.addInnerInterceptor(mysqlPaginationInterceptor);
        return interceptor;
    }

}
