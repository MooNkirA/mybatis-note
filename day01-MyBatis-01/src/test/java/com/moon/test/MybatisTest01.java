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

/**
 * MyBatis测试类
 * @author MoonZero
 */
public class MybatisTest01 {
	
	// 模拟注入sql工厂对象
	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 初始化SqlSessionFactory对象
	 */
	@Before
	public void init() throws IOException {
		// 定义MyBatis配置文件字符串
		String resource = "SqlMapConfig.xml";
		
		// 1. 使用MyBatis提供的Resources类静态方法，得到配置文件输入流
		InputStream inputStream = Resources.getResourceAsStream(resource);
		
		// 2. 创建会话工厂，传入MyBatis的配置文件信息
		// 它是mybatis框架的核心对象，是线程安全的，一个应用中，通常只需要一个（单例设计模式）
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 根据id查询客户信息，返回一条查询记录
	 */
	@Test
	public void findUserByIdTest() throws IOException {
		// 3. 通过工厂得到SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 4. 通过SqlSession操作数据库
		// 第1个参数statement：映射文件中的statement的id，等于映射文件中的namespace+"."+statement的id
		// 第2个参数parameter：指定和映射文件中所匹配的parameterType类型的参数
		// sqlSession.selectOne返回结果是与映射文件中所匹配的resultType类型的对象
		User user = sqlSession.selectOne("test.findUserById", 1);

		System.out.println(user);

		// 5. 关闭sqlSession，释放资源
		sqlSession.close();
	}

	/**
	 * 根据名字查询客户信息，可能返回多条查询记录
	 */
	@Test
	public void findUserByNameTest() throws IOException {
		// 3. 通过工厂得到SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
	
		/* 4. 通过SqlSession操作数据库
		 * sqlSession.selectList:查询多条记录，返回结果是与映射文件中所匹配的resultType类型的对象 
		 * 第1个参数statement：映射文件中的statement的id，等于映射文件中的namespace+"."+statement的id
		 * 第2个参数parameter：指定和映射文件中所匹配的parameterType类型的参数
		 */
		List<User> users = sqlSession.selectList("test.findUserByName", "%小明%");
	
		for (User user : users) {
			System.out.println(user);
		}
	
		// 5. 关闭sqlSession，释放资源
		sqlSession.close();
	}

	/**
	 * 添加客户数据
	 */
	@Test
	public void insertUserTest() throws IOException {
		// 3. 通过工厂得到SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
	
		// 创建sqlSession对象，指定自动提交事务。true：提交；false：不提交。默认false
		// SqlSession sqlSession = sqlSessionFactory.openSession(true);
		
		// 4. 通过SqlSession操作数据库
		User user = new User();
		user.setUsername("测试一下");
		user.setAddress("123456");
		user.setBirthday(new Date());
	
		// 第1个参数statement：映射文件中的statement的id，等于映射文件中的namespace+"."+statement的id
		// 第2个参数parameter：指定和映射文件中所匹配的parameterType类型的参数
		// sqlSession.insert("test.insertUser", user);
		sqlSession.insert("test.insertUser2", user);
	
		// 5. 提交事务（默认是关闭自动提交）
		sqlSession.commit();
		System.out.println(user.getId());
		
		// 6. 关闭sqlSession，释放资源
		sqlSession.close();
	}

	/**
	 * 删除客户数据
	 */
	@Test
	public void deleteUserTest() throws IOException {
		// 3. 通过工厂得到SqlSession(开启自动提交事务)
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
	
		// 4. 通过SqlSession操作数据库
		// 第1个参数statement：映射文件中的statement的id，等于映射文件中的namespace+"."+statement的id
		// 第2个参数parameter：指定和映射文件中所匹配的parameterType类型的参数
		sqlSession.delete("test.deleteUser", 30);
	
		// 6. 关闭sqlSession，释放资源
		sqlSession.close();
	}

	/**
	 * 更新客户数据
	 */
	@Test
	public void updateUserTest() throws IOException {
		// 3. 通过工厂得到SqlSession(开启自动提交事务)
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
	
		// 4. 通过SqlSession操作数据库
		// 第1个参数statement：映射文件中的statement的id，等于映射文件中的namespace+"."+statement的id
		// 第2个参数parameter：指定和映射文件中所匹配的parameterType类型的参数
		// 创建客户对象,必须设置id
		User user = new User();
		user.setId(30);
		user.setUsername("测试一下2");
		user.setAddress("654321");
	
		// 调用更新方法
		sqlSession.update("test.updateUser", user);
	
		// 6. 关闭sqlSession，释放资源
		sqlSession.close();
	}
}
