package com.moon.mybatisplus.samples.association.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 角色表实体
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-09 15:15
 * @description
 */
@Data
@TableName("role")
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
    /**
     * 角色的用户列表。此属性用于联合查询
     */
    private List<User> users;

}
