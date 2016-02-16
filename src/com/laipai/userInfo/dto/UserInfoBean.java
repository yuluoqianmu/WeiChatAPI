package com.laipai.userInfo.dto;

import java.sql.Timestamp;

import javax.persistence.Entity;


/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */
public class UserInfoBean implements java.io.Serializable {

	// Fields

	private String userId;
	private String userName;
	private String userPass;
	private Integer userStat;
	

	// Constructors

	/** default constructor */
	public UserInfoBean() {
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return this.userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public Integer getUserStat() {
		return this.userStat;
	}

	public void setUserStat(Integer userStat) {
		this.userStat = userStat;
	}


}