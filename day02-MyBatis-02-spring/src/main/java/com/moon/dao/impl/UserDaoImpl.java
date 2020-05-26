package com.moon.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.moon.dao.IUserDao;
import com.moon.entity.User;

/**
 * 原生开发方式：dao接口实现类
 */
// 整合包提供了一个SqlSessionDaoSupport类，dao实现类需要继承它，获取SqlSession对象
public class UserDaoImpl extends SqlSessionDaoSupport implements IUserDao {

	@Override
	public User findUserById(Integer id) {
		// 1.创建sqlSession
		SqlSession sqlSession = this.getSqlSession();

		// 2.执行sql语句
		User user = sqlSession.selectOne("test.findUserById", id);
		
		// 3.与spring整合以后，sqlSession对象，交给spring管理，不需要再手动释放（不能手动释放）
		// sqlSession.close();

		return user;
	}

	@Override
	public void insertUser(User user) {
		// 1.创建sqlSession
		SqlSession sqlSession = getSqlSession();

		// 2.执行sql语句
		sqlSession.insert("test.insertUser", user);

		// 3.与spring整合以后，sqlSession对象，交给spring管理，不需要再关心事务
		//（如果配置了spring的事务，它就使用spring的事务；如果没有配置spring事务，它就使用jdbc事务）
	}

}
