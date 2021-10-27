package com.moon.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    // 配置主键生成策略
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long roleId;

    // 配置 @TableField 注解的 exist 属性为false，代表该字段在数据库表中不存在
    @TableField(exist = false)
    private String address;
}
