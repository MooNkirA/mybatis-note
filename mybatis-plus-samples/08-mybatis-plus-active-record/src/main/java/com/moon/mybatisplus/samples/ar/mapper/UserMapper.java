package com.moon.mybatisplus.samples.ar.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.mybatisplus.samples.ar.entity.User;

/**
 * 用户表持久接口。
 * 使用 ActiveRecord 模式时，此接口就算没有使用，也必须定义。
 * 如不定义此接口，则默认不注入
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 9:48
 * @description
 */
public interface UserMapper extends BaseMapper<User> {
}
