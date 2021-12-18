package com.moon.mybatisplus.samples.performance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * user 表实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 15:13
 * @description
 */
@TableName("user")
@Data
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long roleId;

}
