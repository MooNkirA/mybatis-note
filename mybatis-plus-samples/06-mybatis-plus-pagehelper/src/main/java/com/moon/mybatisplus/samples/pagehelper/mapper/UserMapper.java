package com.moon.mybatisplus.samples.pagehelper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.mybatisplus.samples.pagehelper.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * user 表持久接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-10-28 9:40
 * @description
 */
@Mapper // 通过@Mapper注解增加到容器
public interface UserMapper extends BaseMapper<User> {
}
