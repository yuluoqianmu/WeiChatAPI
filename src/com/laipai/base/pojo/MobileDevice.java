package com.laipai.base.pojo;

import javax.persistence.Entity;

/**
 * MobileDevice entity. @author MyEclipse Persistence Tools
 */
@Entity
public class MobileDevice implements java.io.Serializable {

	// Fields

	private String mobileDeviceId;
	private String userId;
	private String mobileId;
	private Integer mobileOsType;
	private Integer isExit;
	private String token;
	private Long insertTime;
	private Long lastLoginTime;
	private Long lastActivityTime;
	private Integer appType;

	// Constructors

	/** default constructor */
	public MobileDevice() {
	}

	// Property accessors
	
	public String getMobileDeviceId() {
		return mobileDeviceId;
	}
	public void setMobileDeviceId(String mobileDeviceId) {
		this.mobileDeviceId = mobileDeviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobileId() {
		return this.mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}

	public Integer getMobileOsType() {
		return this.mobileOsType;
	}

	public void setMobileOsType(Integer mobileOsType) {
		this.mobileOsType = mobileOsType;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Long insertTime) {
		this.insertTime = insertTime;
	}

	public Long getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getLastActivityTime() {
		return this.lastActivityTime;
	}

	public void setLastActivityTime(Long lastActivityTime) {
		this.lastActivityTime = lastActivityTime;
	}

	public Integer getIsExit() {
		return isExit;
	}

	public void setIsExit(Integer isExit) {
		this.isExit = isExit;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}

}