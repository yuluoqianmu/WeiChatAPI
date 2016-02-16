package com.laipai.userInfo.service.imple;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.userInfo.dao.UserDao;
import com.laipai.userInfo.pojo.UserInfo;
import com.laipai.userInfo.service.IUserService;

/**
 * add by zhanhh on$2014-12-12
 * 用户信息数据处理类
 * */
//配置文件会自动扫描此目录下@Service标记的类，若找到，则标记为bean
@Service("userService")
public class UserServiceImple implements IUserService{
	@Autowired
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void test() {
		System.out.println("hello world!");
	}
	/**
	 * 保存用户
	 * */
	public Serializable save(UserInfo user) {
		return userDao.save(user);
	}
}
