package com.moon.mybatisplus.samples.servicecrud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.mybatisplus.samples.servicecrud.entity.User;

/**
 * 自定义业务层接口，继承 MP 的 IService 接口，
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-02-19 11:39
 * @description
 */
// 此接口需要继承 IService 接口，否则使用接口注入的时候就无法调用相关基础方法
public interface IUserService extends IService<User> {
    // 此接口中可以自定义相关的业务方法...
}
