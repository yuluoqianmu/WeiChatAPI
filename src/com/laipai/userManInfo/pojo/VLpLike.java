package com.laipai.userManInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

import com.laipai.galaryManInfo.pojo.LpGalary;


/**
 * LpLike entity. @author MyEclipse Persistence Tools
 */
@Entity
public class VLpLike implements java.io.Serializable {

	// Fields
    private String likeId;
    private String userId;
	private String gallaryId;
	private Timestamp likeTime;
	private int likeStatus;
	private Integer likeType;
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

	public Timestamp getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(Timestamp likeTime) {
		this.likeTime = likeTime;
	}

	public int getLikeStatus() {
		return likeStatus;
	}

	public void setLikeStatus(int likeStatus) {
		this.likeStatus = likeStatus;
	}

	public Integer getLikeType() {
		return likeType;
	}

	public void setLikeType(Integer likeType) {
		this.likeType = likeType;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getGallaryId() {
		return gallaryId;
	}

	public void setGallaryId(String gallaryId) {
		this.gallaryId = gallaryId;
	}


	
}