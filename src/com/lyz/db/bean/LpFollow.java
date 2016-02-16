package com.lyz.db.bean;

import java.sql.Date;

public class LpFollow {
	/**/
	private String followId;

	/*用户Id*/
	private String userId;

	/*摄影师Id*/
	private String cameraId;

	/*关注时间*/
	private Date followTime;

	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public Date getFollowTime() {
		return followTime;
	}

	public void setFollowTime(Date followTime) {
		this.followTime = followTime;
	}
	
	
}
