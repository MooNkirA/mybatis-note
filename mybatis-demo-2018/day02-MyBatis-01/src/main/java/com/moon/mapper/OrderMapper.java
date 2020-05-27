package com.moon.mapper;

import java.util.List;

import com.moon.entity.Order;

/**
 * MyBatis：Mapper动态代理方式开发dao
 * @author MoonZero
 */
public interface OrderMapper {

	/**
	 * 查询所有订单
	 */
	List<Order> queryAllOrders();

	/**
	 * 查询所有订单（使用resultMap）
	 */
	List<Order> queryAllOrdersByResultMap();

	/**
	 * 查询订单数据，并且关联查询出所属的用户数据
	 */
	List<Order> queryOrdersAndUsers();
}
