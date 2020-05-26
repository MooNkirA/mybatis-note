package com.moon.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.moon.entity.User;
import com.moon.mapper.UserMapper;

/**
 * 动态代理mapper开发dao测试类
 * @author MoonZero
 */
public class MyBatisTest03_Mapper {
	
	// 初始化时注入sqlSessionFactory
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * 运行测试方法前创建工厂(模拟使用spring整合)
	 */
	@Before
	public void init() throws IOException {
		// 定义MyBatis配置文件字符串
		String config = "SqlMapConfig.xml";
		// 获取输入流对象
		InputStream is = Resources.getResourceAsStream(config);

		// 创建会话工厂
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
	}

	
	/**
	 * 测试查询单个对象方法
	 */
	@Test
	public void findUserByIdTest() {
		// 1.创建sqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		// 2.从sqlSession对象获取mapper代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		
		// 3.调用动态代理的方法
		User user = mapper.findUserById(1);
		System.out.println(user);

		// 4.释放资源
		sqlSession.close();
	}

	/**
	 * 测试模糊查询
	 */
	@Test
	public void findUserByNameTest() {
		// 1.创建sqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		// 2.从sqlSession对象获取mapper代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		
		// 3.调用动态代理的方法
		List<User> users = mapper.findUserByName("%小明%");
		// 遍历集合
		if (users != null && users.size() > 0) {
			for (User user : users) {
				System.out.println(user);
			}
		}
		
		// 4.释放资源
		sqlSession.close();
	}

	/**
	 * 测试插入数据
	 */
	@Test
	public void insertUserTest() {
		// 创建插入的对象
		User user = new User();
		user.setUsername("测试一下");
		user.setAddress("123456");
		user.setBirthday(new Date());
		
		// 1.创建sqlSession对象(开启自动提交事务)
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		
		// 2.从sqlSession对象获取mapper代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		
		// 3.调用动态代理的方法
		mapper.insertUser(user);
		
		// 4.释放资源
		sqlSession.close();
	}
	
	
	/** ============扩展===============
	 * 测试一级缓存
	 */
	@Test
	public void oneCacheTest() {
		// 1.创建sqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		// 2.从sqlSession对象获取mapper代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		
		// 3.调用动态代理的方法
		User user = mapper.findUserById(1);
		System.out.println(user);
	
		System.out.println("===================");
		
		user = mapper.findUserById(1);
		System.out.println(user);
		
		// 4.释放资源
		sqlSession.close();
	}
	
	/** 
	 * 测试二级缓存
	 */
	@Test
	public void secondCacheTest() {
		// 1.创建sqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		// 2.从sqlSession对象获取mapper代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		
		// 3.调用动态代理的方法
		User user = mapper.findUserById(1);
		System.out.println(user);
		
		// 测试二级缓存，一定要关闭sqlSession
		sqlSession.close();
	
		System.out.println("===================");
		
		sqlSession = sqlSessionFactory.openSession();
		mapper = sqlSession.getMapper(UserMapper.class);
		user = mapper.findUserById(1);
		System.out.println(user);
		
		// 4.释放资源
		sqlSession.close();
	}
	
	/**
	 * 使用注解方式开发
	 */
	@Test
	public void queryUserById_annotationTest() {
		// 1.创建sqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		// 2.从sqlSession对象获取mapper代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		
		// 3.调用动态代理的方法
		User user = mapper.queryUserById_annotation(1);
		System.out.println(user);
		
		// 4.释放资源
		sqlSession.close();
	}
}
