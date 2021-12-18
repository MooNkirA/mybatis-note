package com.moon.mybatisplus.samples.quickstart.entity;

import lombok.Data;

/**
 * 用户实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-19 23:06
 * @description
 */
@Data
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long roleId;

}
