package com.moon.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.moon.dao.IUserDao;
import com.moon.entity.User;

/**
 * 原生开发方式：dao接口实现类
 */
public class UserDaoImpl implements IUserDao {

	// 手动注入sqlSessionFactory
	private SqlSessionFactory sqlSessionFactory;

	// 创建构造方法
	public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super();
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public User findUserById(Integer id) {
		// 创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 执行sql语句
		User user = sqlSession.selectOne("findUserById", id);
		// 释放资源
		sqlSession.close();

		return user;
	}

	@Override
	public List<User> findUserByName(String name) {
		// 创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();

		// 执行sql语句
		List<User> users = sqlSession.selectList("findUserByName", name);
		// 释放资源
		sqlSession.close();

		return users;
	}

	@Override
	public void insertUser(User user) {
		// 创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession(true);

		// 执行sql语句
		sqlSession.insert("insertUser", user);

		// 释放资源
		sqlSession.close();
	}

}
