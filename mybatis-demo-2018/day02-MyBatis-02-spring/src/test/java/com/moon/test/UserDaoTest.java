package com.moon.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.moon.dao.IUserDao;
import com.moon.entity.User;

/**
 * 使用MyBatis原始dao开发测试
 * @author MoonZero
 */
public class UserDaoTest {
	
	/**
	 * 测试根据用户id查询用户
	 */
	@Test
	public void queryUserByIdTest() {
		// 1.加载spring配置文件，创建spring容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		
		// 2.从spring容器中获取dao对象
		IUserDao userDao = (IUserDao) ac.getBean("userDao");
		
		// 3.调用查询方法
		User user = userDao.findUserById(1);
		System.out.println(user);
	}
	
	/**
	 * 测试插入用户
	 */
	@Test
	public void insertUserTest() {
		// 1.加载spring配置文件，创建spring容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		
		// 2.从spring容器中获取dao对象
		IUserDao userDao = (IUserDao) ac.getBean("userDao");
		
		// 3.创建用户对象，调用插入方法
		User user = new User();
		user.setUsername("主宰2");
		user.setAddress("Dota2");
		user.setBirthday(new Date());
		user.setSex("1");
		
		userDao.insertUser(user);
	}
}
