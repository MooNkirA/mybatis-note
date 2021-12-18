package com.moon.mybatisplus.samples.pagehelper.config;

import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-Plus 配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-29 14:49
 * @description
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * pagehelper的分页插件。
     * mp 的分页插件（MybatisPlusInterceptor）与 pagehelper 存在依赖 jsqlparser 冲突，不建议混用
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }

}
