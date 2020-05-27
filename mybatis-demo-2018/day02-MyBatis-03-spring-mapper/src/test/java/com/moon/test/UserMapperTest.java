package com.moon.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.moon.entity.User;
import com.moon.entity.UserExample;
import com.moon.entity.UserExample.Criteria;
import com.moon.mapper.UserMapper;

/**
 * 使用MyBatis Mapper代理开发测试
 * @author MoonZero
 */
public class UserMapperTest {
	
	/**
	 * 测试根据用户名称模糊查询用户
	 */
	@Test
	public void queryUserByNameTest() {
		// 1.加载spring配置文件，创建spring容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		
		// 2.从spring容器中获取用户mapper代理对象
		UserMapper mapper = (UserMapper) ac.getBean("userMapper");
		
		// 3.使用mapper代理对象调用查询方法
		// 3.1 使用代理对象创建辅助类对象
		UserExample userExample = new UserExample();
		
		// 3.2 使用辅助类，创建Criteria对象
		Criteria criteria = userExample.createCriteria();
		
		// 3.3 使用criteria对象，调用andXxx()方法，设置条件
		criteria.andUsernameLike("%小明%");

		// 3.4 使用mapper代理对象调用查询方法
		List<User> list = mapper.selectByExample(userExample);
		for (User user : list) {
			System.out.println(user);
		}
	}
	
	/**
	 * 测试插入用户
	 */
	@Test
	public void insertUserTest() {
		// 1.加载spring配置文件，创建spring容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		
		// 2.从spring容器中获取用户mapper代理对象
		UserMapper mapper = (UserMapper) ac.getBean("userMapper");
		
		// 3.创建用户对象，使用代理对象调用插入方法
		User user = new User();
		user.setUsername("幻影刺客");
		user.setAddress("Dota2?");
		user.setBirthday(new Date());
		user.setSex("0");
		
		mapper.insertSelective(user);
	}
}
