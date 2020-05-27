package com.moon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.moon.entity.User;

/**
 * MyBatis：Mapper动态代理方式开发dao
 * @author MoonZero
 */
public interface UserMapper {

	/**
	 * 根据id查询
	 */
	User findUserById(Integer id);
	
	/**
	 * 根据用户名模糊查询
	 */
	List<User> findUserByName(String username);
	
	/**
	 * 插入数据
	 */
	void insertUser(User user);
	
	/**
	 * 根据用户id查询用户（使用注解开发）
	 */
	@Select("select * from `user` where id=#{id}")
	User queryUserById_annotation(Integer userId);

}
