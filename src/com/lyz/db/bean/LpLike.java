package com.lyz.db.bean;

import java.sql.Date;

public class LpLike {
	
	/**/
	private String likeId;

	/*用户ID*/
	private String userId;

	/*作品集ID*/
	private String galaryId;

	/*喜欢时间*/
	private Date likeTime;

	/*喜欢状态*/
	private byte likeStatus;

	/*0：作品集，1：来拍摄帖子*/
	private int likeType;

	/*来拍社ID*/
	private String newsId;

	public String getLikeId() {
		return likeId;
	}

	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGalaryId() {
		return galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public Date getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(Date likeTime) {
		this.likeTime = likeTime;
	}

	public byte getLikeStatus() {
		return likeStatus;
	}

	public void setLikeStatus(byte likeStatus) {
		this.likeStatus = likeStatus;
	}

	public int getLikeType() {
		return likeType;
	}

	public void setLikeType(int likeType) {
		this.likeType = likeType;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	
	
}
