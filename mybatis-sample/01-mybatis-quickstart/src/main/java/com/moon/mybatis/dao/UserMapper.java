package com.moon.mybatis.dao;

import com.moon.mybatis.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * user 表 Mapper映射器接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-13 17:06
 * @description
 */
public interface UserMapper {

    /* 基础查询 */
    List<User> queryAllUser();

}
