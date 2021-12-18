package com.moon.mybatisplus.samples.injector.mapper;

import com.moon.mybatisplus.samples.injector.base.MyBaseMapper;
import com.moon.mybatisplus.samples.injector.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表持久接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-10 15:21
 * @description
 */
@Mapper
public interface UserMapper extends MyBaseMapper<User> {
}
