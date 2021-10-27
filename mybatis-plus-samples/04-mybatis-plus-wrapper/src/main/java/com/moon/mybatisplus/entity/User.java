package com.moon.mybatisplus.entity;

import lombok.Data;

/**
 * user 表实体类
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-26 21:10
 * @description
 */
@Data
public class User {

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 角色ID
     */
    private Long roleId;

}
