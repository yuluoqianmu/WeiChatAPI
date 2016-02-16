package com.laipai.userInfo.dao;

import java.io.Serializable;

import com.laipai.userInfo.pojo.UserInfo;


public interface UserDao {
	public Serializable save(UserInfo user);
}
