package com.moon.mybatisplus.samples.wrapper.entity;

import lombok.Data;

/**
 * role 表实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-26 21:11
 * @description
 */
@Data
public class Role {

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDescribe;

}
