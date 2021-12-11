package com.moon.mybatisplus.samples.execution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.mybatisplus.samples.execution.entity.User;
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
public interface UserMapper extends BaseMapper<User> {
}
