package com.moon.dao;

import java.util.List;

import com.moon.entity.User;

/**
 * 原生dao接口
 * @author MoonZero
 */
public interface IUserDao {
	
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
	
}
