package com.moon.mybatisplus.samples.optlocker.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MP 配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 17:21
 * @description
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 旧版配置乐观锁插件 OptimisticLockerInterceptor
     */
    /*@Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }*/

    /**
     * 新版，配置乐观锁插件 OptimisticLockerInnerInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mybatisPlusInterceptor;
    }

}
