package com.moon.mybatisplus.samples.pagination.entity;

import lombok.Data;

/**
 * user 表实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-27 22:38
 * @description
 */
@Data
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;

}
