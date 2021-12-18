package com.moon.mybatisplus.samples.execution.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MP 配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 15:11
 * @description
 */
@Configuration
// @MapperScan("com.moon.mybatisplus.samples.execution.mapper")
public class MybatisPlusConfig {

    /**
     * 增加 MP 内置的执行分析插件 BlockAttackInnerInterceptor（攻击 SQL 阻断解析器），防止全表更新与删除
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 配置执行分析插件 SqlExplainInterceptor。
     * 此插件在 mybatis-plus 3.1.1 版本时可用
     *
     * @return
     */
    /*@Bean
    public SqlExplainInterceptor sqlExplainInterceptor(){
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);
        return sqlExplainInterceptor;
    }*/

}
