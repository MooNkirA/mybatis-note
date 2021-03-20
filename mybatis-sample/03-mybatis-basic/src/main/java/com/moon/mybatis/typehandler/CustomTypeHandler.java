package com.moon.mybatis.typehandler;

import com.moon.mybatis.pojo.User;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * 自定义类型处理器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-19 14:57
 * @description
 */
@MappedJdbcTypes({JdbcType.DATE, JdbcType.INTEGER})
@MappedTypes(User.class)
public class CustomTypeHandler {
}
