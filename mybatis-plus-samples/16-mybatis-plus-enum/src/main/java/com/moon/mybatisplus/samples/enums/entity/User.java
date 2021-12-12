package com.moon.mybatisplus.samples.enums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.mybatisplus.samples.enums.enums.AgeEnum;
import com.moon.mybatisplus.samples.enums.enums.RoleEnum;
import lombok.Data;

/**
 * user 表实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-12 8:57
 * @description
 */
@TableName("user")
@Data
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;

    /**
     * IEnum接口的枚举处理
     * 数据库字段：age INT(11)
     */
    private AgeEnum age;
    private String email;

    /**
     * 原生枚举（带{@link com.baomidou.mybatisplus.annotation.EnumValue} 注解)
     * 数据库的值对应该注解对应的属性
     * 数据库字段：role_id BIGINT(20)
     */
    private RoleEnum roleId;

}
