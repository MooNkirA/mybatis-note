package com.moon.mybatisplus.samples.crud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.mybatisplus.samples.crud.entity.User;

/**
 * MP 支持不需要 UserMapper.xml 这个模块是使用内置 CRUD ，不需要配置 XML 映射文件
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-22 16:29
 * @description
 */
public interface UserMapper extends BaseMapper<User> {
}
