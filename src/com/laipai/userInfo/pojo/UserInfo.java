package com.laipai.userInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

import com.lyz.util.LaiPaiEntityIdGenerator;


/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
public class UserInfo implements java.io.Serializable {

	// Fields

	private String userId;
	private String userName;
	private String userPass;
	private Integer userStat;
	
	// Constructors

	/** default constructor */
	public UserInfo() {
		userId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeUser)+"";
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