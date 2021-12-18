package com.moon.mybatisplus.samples.association.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.mybatisplus.samples.association.entity.Role;

import java.util.List;

/**
 * 角色表持久接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-09 15:12
 * @description
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> queryAllRole();

}
