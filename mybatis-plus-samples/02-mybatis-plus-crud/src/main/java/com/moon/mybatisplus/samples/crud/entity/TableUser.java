package com.moon.mybatisplus.samples.crud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * User表实体类，用于测试表名与类名不一致，字段名与属性名不一致等情况
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-25 11:45
 * @description
 */
@Data
@TableName("user")
public class TableUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 配置 @TableField 注解的 value 属性，解决字段与数据库表不一致
    @TableField(value = "name")
    private String username;
    private Integer age;
    // 配置 @TableField 注解的 select 属性控制是否查询该字段，值为 false 则不加入 select 查询范围
    @TableField(select = false)
    private String email;
    private Long roleId;

}
