package com.moon.mybatisplus.samples.sequence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户表实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 9:37
 * @description
 */
@TableName("TB_USER")
@Data
/*
 * @KeySequence 注解用于指定序列的名称
 * 3.1.2 版本后， clazz 属性自动匹配,无需指定，后面也移除了该属性
 *      @KeySequence(value = "SEQ_USER", clazz = String.class)
 */
@KeySequence(value = "SEQ_USER")
public class User {

    // 配置主键生成策略，必须使用 INPUT
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long roleId;

}
