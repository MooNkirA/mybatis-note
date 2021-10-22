package com.moon.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 用户实体类，对应user表
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-19 23:06
 * @description
 */
@Data
public class User {

    private Long id;

    // 配置 @TableField 注解的 value 属性，解决字段与数据库表不一致
    @TableField(value = "name")
    private String username;
    private Integer age;
    private String email;

    // 配置 @TableField 注解的 exist 属性为false，代表该字段在数据库表中不存在
    @TableField(exist = false)
    private String address;
}
