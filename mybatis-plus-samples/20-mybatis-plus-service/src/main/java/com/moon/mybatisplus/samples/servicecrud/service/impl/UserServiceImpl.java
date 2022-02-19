package com.moon.mybatisplus.samples.servicecrud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.mybatisplus.samples.servicecrud.entity.User;
import com.moon.mybatisplus.samples.servicecrud.mapper.UserMapper;
import com.moon.mybatisplus.samples.servicecrud.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * 业务接口实现
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-02-19 11:39
 * @description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
