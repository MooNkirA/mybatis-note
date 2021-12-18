package com.moon.mybatisplus.samples.association.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.moon.mybatisplus.samples.association.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表持久接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-12-09 15:12
 * @description
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> queryUserRoleByPage(IPage<User> page, @Param(Constants.WRAPPER) Wrapper<User> wrapper);

}
