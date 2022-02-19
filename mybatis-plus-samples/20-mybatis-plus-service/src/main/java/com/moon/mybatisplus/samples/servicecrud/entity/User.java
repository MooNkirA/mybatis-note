package com.moon.mybatisplus.samples.servicecrud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类，对应user表
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-19 23:06
 * @description
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -8142647747810148977L;
    // 配置主键生成策略
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long roleId;
}
