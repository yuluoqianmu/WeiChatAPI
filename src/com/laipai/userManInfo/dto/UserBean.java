package com.laipai.userManInfo.dto;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;



/**
 * LpUser entity. @author MyEclipse Persistence Tools
 */
@Entity
public class UserBean implements java.io.Serializable {

	private String userId;

	private String userName;
	
	private Integer userType;
	
	private String nickName;
	private String nickName2;
	
	private String followId; 
	
	private String headImage;

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getUserId() {
		return userId;
	}

	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName2() {
		return nickName2;
	}

	public void setNickName2(String nickName2) {
		this.nickName2 = nickName2;
	}
	
}