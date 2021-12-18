package com.moon.mybatisplus.samples.pagination.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MP 配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-27 21:56
 * @description
 */
@Configuration
@MapperScan("com.moon.mybatisplus.samples.pagination.mapper")
public class MybatisPlusConfig {

    // MyBatis-Plus的分页插件(旧版)
    /*@Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }*/

    /**
     * 新的分页插件，一缓和二缓遵循mybatis的规则，需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor mysqlPaginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL); // 此示例使用MySQL数据库
        // mysqlPaginationInterceptor.setOverflow(true); // 溢出总页数后是否进行处理(默认为false，不处理，可参考 插件#continuePage 方法)
        // mysqlPaginationInterceptor.setMaxLimit(1024L); // 单页分页条数限制(默认无限制，可参考 插件#handlerLimit 方法)
        // mysqlPaginationInterceptor.setDbType(DbType.MYSQL); // 数据库类型(根据类型获取应使用的分页方言，可参考 插件#findIDialect 方法)
        // mysqlPaginationInterceptor.setDialect(new MySqlDialect()); // 方言实现类(可参考 插件#findIDialect 方法)
        interceptor.addInnerInterceptor(mysqlPaginationInterceptor);
        return interceptor;
    }

}
