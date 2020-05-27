package com.moon.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.moon.entity.QueryVo;
import com.moon.entity.User;
import com.moon.mapper.UserMapper;

/**
 * 包装类型输入测试 
 * @author MoonZero
 */
public class UserMapperTest {

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
	 * 1.测试使用包装类型的进行模糊查询
	 */
	@Test
	public void queryUserByConditionTest() {
		// 1.创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 2.获取接口mapper动态代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);

		// 3.调用接口方法
		// 创建包装类
		QueryVo queryVo = new QueryVo();

		// 创建用户对象
		User user = new User();
		user.setUsername("小明");

		// 将用户对象设置到包装类中
		queryVo.setUser(user);

		List<User> list = mapper.queryUserByCondition(queryVo);
		if (list != null && list.size() > 0) {
			for (User u : list) {
				System.out.println(u);
			}
		}

		// 4.关闭资源 
		sqlSession.close();
	}

	/**
	 * 2.测试统计查询，返回类型是基本数据类型
	 */
	@Test
	public void countUsersTest() {
		// 1.创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 2.获取接口mapper动态代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);

		// 3.调用接口方法
		int count = mapper.countUsers();
		System.out.println("当前用户数量：" + count);

		// 4.关闭资源 
		sqlSession.close();
	}

	/**
	 * 3.根据用户名称和性别查询用户 (使用动态sql查询)
	 */
	@Test
	public void queryUserByNameAndSexTest() {
		// 1.创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 2.获取接口mapper动态代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);

		// 3.调用接口方法
		// 创建用户对象
		User user = new User();
		// user.setUsername("%小%");
		user.setSex("1");

		List<User> users = mapper.queryUserByNameAndSex(user);
		for (User user2 : users) {
			System.out.println(user2);
		}

		// 4.关闭资源 
		sqlSession.close();
	}

	/**
	 * 4.动态更新用户 (使用动态sql更新)
	 */
	@Test
	public void dynamicUpdateUserTest() {
		// 1.创建sqlSession（设置自动提交事务）
		SqlSession sqlSession = sqlSessionFactory.openSession(true);

		// 2.获取接口mapper动态代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);

		// 3.调用接口方法
		// 创建用户对象
		User user = new User();
		user.setId(32);
		user.setUsername("敌法师");
		user.setSex("1");

		mapper.dynamicUpdateUser(user);

		// 4.关闭资源 
		sqlSession.close();
	}

	/**
	 * 5.批量新增用户(使用动态sql新增)
	 */
	@Test
	public void batchInsertUsersTest() {
		// 1.创建sqlSession（设置自动提交事务）
		SqlSession sqlSession = sqlSessionFactory.openSession(true);

		// 2.获取接口mapper动态代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);

		// 3.调用接口方法
		// 创建集合存放用户对象
		List<User> list = new ArrayList<User>();

		for (int i = 0; i < 3; i++) {
			User user = new User();
			user.setUsername("敌法师" + i);
			user.setSex("1");
			user.setBirthday(new Date());
			user.setAddress("dota2");
			list.add(user);
		}

		mapper.batchInsertUsers(list);

		// 4.关闭资源 
		sqlSession.close();
	}

	/**
	 * 6.批量删除用户(使用动态sql删除)
	 */
	@Test
	public void batchDeleteUsersTest() {
		// 1.创建sqlSession（设置自动提交事务）
		SqlSession sqlSession = sqlSessionFactory.openSession(true);

		// 2.获取接口mapper动态代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);

		// 3.调用接口方法
		// 创建id数组
		Integer[] ids = { 38, 39, 40 };

		mapper.batchDeleteUsers(ids);

		// 4.关闭资源 
		sqlSession.close();
	}

	/**
	 * 7.测试多表关联查询（一对多）
	 */
	@Test
	public void queryUsersAndOrdersTest() {
		// 1.创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
	
		// 2.获取接口mapper动态代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
	
		// 3.调用接口方法
		List<User> list = mapper.queryUsersAndOrders();
		if (list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	
		// 4.关闭资源 
		sqlSession.close();
	}
}
