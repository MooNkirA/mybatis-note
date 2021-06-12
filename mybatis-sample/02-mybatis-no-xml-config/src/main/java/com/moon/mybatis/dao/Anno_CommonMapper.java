package com.moon.mybatis.dao;

import com.moon.mybatis.pojo.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 使用注解映射sql，不依赖xml
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-12 15:47
 * @description
 */
public interface Anno_CommonMapper {

    @Results(id = "BaseResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "address", column = "address")
    })
    @Select("select `id`, `username`, `birthday`, `sex`, `address` from `user`")
    List<User> queryAllUser();

}
