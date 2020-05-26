package com.moon.mapper;

import java.util.List;

import com.moon.entity.QueryVo;
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
	List<User> queryUserByCondition(QueryVo queryVo);

	/**
	 * 插入数据
	 */
	void insertUser(User user);

	/**
	 * 统计用户数量
	 */
	int countUsers();

	/**
	 * 根据用户名称和性别查询用户 
	 */
	List<User> queryUserByNameAndSex(User user);

	/**
	 * 动态更新用户
	 */
	void dynamicUpdateUser(User user);

	/**
	 * 批量新增用户
	 */
	void batchInsertUsers(List<User> list);

	/**
	 * 批量删除用户
	 */
	void batchDeleteUsers(Integer[] ids);

	/**
	 * 查询用户数据，并且关联查询出用户的所有订单数据 
	 */
	List<User> queryUsersAndOrders();
}
