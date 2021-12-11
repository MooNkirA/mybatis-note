package com.moon.mybatisplus.samples.autofill.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 自定义填充处理器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-11 21:08
 * @description
 */
@Slf4j
// 自定义填充处理器在 Spring Boot 项目中需要声明 `@Component` 或 `@Bean` 注册到 spring 容器才能生效
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        // 此方法指定需要自动填充的字段名称与填充的内容
        this.strictInsertFill(metaObject, "roleId", Long.class, 1L);
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 因为此示例只设置插入时自动填充，所以此方法为空即可
    }

}
