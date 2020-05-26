package com.moon.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.moon.entity.Order;
import com.moon.mapper.OrderMapper;

/**
 * 包装类型输入测试 
 * @author MoonZero
 */
public class OrderMapperTest {

	// 初奴化sqlFactory工厂
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * 运行时初始化创建工厂
	 * @throws IOException 
	 */
	@Before
	public void init() throws IOException {
		// 定义MyBatis配置字符串
		String config = "SqlMapConfig.xml";

		// 获取输入法对象
		InputStream is = Resources.getResourceAsStream(config);

		// 创建会话工厂
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
	}

	/**
	 * 1.测试查询所有订单，属性与表名不一致的情况
	 */
	@Test
	public void queryAllOrdersTest() {
		// 1.创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 2.获取接口mapper动态代理对象
		OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

		// 3.调用接口方法
		List<Order> list = mapper.queryAllOrders();
		if (list != null && list.size() > 0) {
			for (Order order : list) {
				System.out.println(order);
			}
		}

		// 4.关闭资源 
		sqlSession.close();
	}

	/**
	 * 2.测试查询所有订单（使用resultMap解决属性与表名不一致）
	 */
	@Test
	public void queryAllOrdersByResultMapTest() {
		// 1.创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 2.获取接口mapper动态代理对象
		OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

		// 3.调用接口方法
		List<Order> list = mapper.queryAllOrdersByResultMap();
		if (list != null && list.size() > 0) {
			for (Order order : list) {
				System.out.println(order);
			}
		}

		// 4.关闭资源 
		sqlSession.close();
	}

	/**
	 * 3.测试多表关联查询（一对一）
	 */
	@Test
	public void queryOrdersAndUsersTest() {
		// 1.创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
	
		// 2.获取接口mapper动态代理对象
		OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
	
		// 3.调用接口方法
		List<Order> list = mapper.queryOrdersAndUsers();
		if (list != null && list.size() > 0) {
			for (Order order : list) {
				System.out.println(order);
			}
		}
	
		// 4.关闭资源 
		sqlSession.close();
	}
}
