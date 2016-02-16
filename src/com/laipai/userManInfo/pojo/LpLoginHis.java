package com.laipai.userManInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpLoginHis entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpLoginHis implements java.io.Serializable {

	// Fields

	private String loginId;
	private LpUser lpUser;
	private Timestamp loginTime;
	private Timestamp logoutTime;

	// Constructors

	/** default constructor */
	public LpLoginHis() {
	}

	/** full constructor */
	public LpLoginHis(LpUser lpUser, Timestamp loginTime, Timestamp logoutTime) {
		this.lpUser = lpUser;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
	}

	// Property accessors

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public LpUser getLpUser() {
		return this.lpUser;
	}

	public void setLpUser(LpUser lpUser) {
		this.lpUser = lpUser;
	}

	public Timestamp getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public Timestamp getLogoutTime() {
		return this.logoutTime;
	}

	public void setLogoutTime(Timestamp logoutTime) {
		this.logoutTime = logoutTime;
	}

}