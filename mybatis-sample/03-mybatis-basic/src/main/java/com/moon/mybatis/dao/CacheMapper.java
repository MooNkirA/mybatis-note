package com.moon.mybatis.dao;

import com.moon.mybatis.pojo.User;

import java.util.List;

/**
 * 用于测试自定义缓存专用的 Mapper 接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-15 22:44
 * @description
 */
public interface CacheMapper {

    /* 用于测试自定义缓存 */
    List<User> selectAllUser();

}
