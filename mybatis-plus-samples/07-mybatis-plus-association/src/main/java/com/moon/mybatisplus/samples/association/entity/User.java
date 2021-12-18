package com.moon.mybatisplus.samples.association.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户表实体
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-09 15:14
 * @description
 */
@Data
@TableName("user")
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;
    /*
     * 用户的角色。此属性用于联合查询
     *  通过 @TableField 注解，指定对应关联的表的主键，
     *  在xml映射文件中的sql 将相应关联表的字段起别名，以 “xxx.属性” 的方式
     *  此时数据就会根据关联主键映射对应的关联表实体对象中
     */
    @TableField(value = "role_id", property = "role.id")
    private Role role;

}
