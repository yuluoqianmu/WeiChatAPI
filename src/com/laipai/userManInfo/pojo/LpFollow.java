package com.laipai.userManInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpFollow entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpFollow implements java.io.Serializable {

	// Fields

	private String followId;
	private LpUser lpUserByUserId;
	private LpUser lpUserByCameraId;
	private Timestamp followTime;

	// Constructors

	/** default constructor */
	public LpFollow() {
	}

	/** full constructor */
	public LpFollow(LpUser lpUserByUserId, LpUser lpUserByCameraId,
			Timestamp followTime) {
		this.lpUserByUserId = lpUserByUserId;
		this.lpUserByCameraId = lpUserByCameraId;
		this.followTime = followTime;
	}

	// Property accessors

	public String getFollowId() {
		return this.followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

	public LpUser getLpUserByUserId() {
		return this.lpUserByUserId;
	}

	public void setLpUserByUserId(LpUser lpUserByUserId) {
		this.lpUserByUserId = lpUserByUserId;
	}

	public LpUser getLpUserByCameraId() {
		return this.lpUserByCameraId;
	}

	public void setLpUserByCameraId(LpUser lpUserByCameraId) {
		this.lpUserByCameraId = lpUserByCameraId;
	}

	public Timestamp getFollowTime() {
		return this.followTime;
	}

	public void setFollowTime(Timestamp followTime) {
		this.followTime = followTime;
	}

}