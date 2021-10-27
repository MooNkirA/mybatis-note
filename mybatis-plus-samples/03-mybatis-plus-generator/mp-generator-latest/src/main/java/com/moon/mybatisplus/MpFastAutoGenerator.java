package com.moon.mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.SQLException;

/**
 * MyBatis-Plus 的代码生成器(3.5.1+版本)，通过 AutoGenerator 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码。
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-26 13:58
 * @description
 */
public class MpFastAutoGenerator {

    /**
     * 执行初始化数据库脚本
     */
    public static void before() throws SQLException {
        /*Connection conn = DATA_SOURCE_CONFIG.build().getConn();
        InputStream inputStream = H2CodeGeneratorTest.class.getResourceAsStream("/sql/init.sql");
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        scriptRunner.setAutoCommit(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
        conn.close();*/
    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/mybatis_plus_sample_db?useUnicode=true&useSSL=false&characterEncoding=utf8", "root", "123456");

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
        before();
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) -> {
                    builder.author(scanner.apply("请输入作者名称？")) // 设置作者
                            // .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E://00-Downloads/temp_file/mp-code-generator/src/main/java");
                })
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
                // 策略配置
                .strategyConfig(builder -> builder.addInclude("user"))
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                 */
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
