package com.moon.dao;

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
	 * 插入数据
	 */
	void insertUser(User user);
	
}
