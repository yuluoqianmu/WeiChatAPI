package com.laipai.userInfo.dao.imple;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.laipai.userInfo.dao.UserDao;
import com.laipai.userInfo.pojo.UserInfo;

/**
 * add by zhanhh on$2014-12-12
 * 用户信息数据保存类
 * */
@Repository("userDao")
public class UserDaoImple implements UserDao {
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Serializable save(UserInfo user) {
		return this.getCurrentSession().save(user);
	}

	public void saveOrUpdate(UserInfo user) {
		this.getCurrentSession().saveOrUpdate(user);
	}
	
	
	
}
