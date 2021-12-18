package com.moon.mybatisplus.samples.id.incrementer;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 自定义ID生成器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-12 13:30
 * @description
 */
@Slf4j
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private final AtomicLong al = new AtomicLong(1);

    /**
     * 生成Id
     *
     * @param entity 实体
     * @return id
     */
    @Override
    public Number nextId(Object entity) {
        // 可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
        String bizKey = entity.getClass().getName();
        log.info("bizKey:{}", bizKey);

        MetaObject metaObject = SystemMetaObject.forObject(entity);
        BigInteger amt = (BigInteger) metaObject.getValue("amt");

        final long id = al.getAndAdd(1);
        log.info("为金额 {} 生成主键值 ->: {}", amt, id);
        return id;
    }

    /**
     * 生成uuid
     *
     * @param entity 实体
     * @return uuid
     */
    @Override
    public String nextUUID(Object entity) {
        return IdentifierGenerator.super.nextUUID(entity);
    }

}
