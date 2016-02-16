package com.laipai.userManInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

import com.laipai.galaryManInfo.pojo.LpGalary;


/**
 * LpLike entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpLike implements java.io.Serializable {

	// Fields

	private String likeId;
	private LpUser lpUser;
	private LpGalary lpGalary;
	private Timestamp likeTime;
	private int likeStatus;
	private Integer likeType;
	private String newsId;

	// Constructors

	/** default constructor */
	public LpLike() {
	}

	/** full constructor */
	public LpLike(LpUser lpUser, LpGalary lpGalary, Timestamp likeTime,
			Boolean likeStatus, Integer likeType, String newsId) {
		this.lpUser = lpUser;
		this.lpGalary = lpGalary;
		this.likeTime = likeTime;
		
		this.likeType = likeType;
		this.newsId = newsId;
	}

	// Property accessors

	public String getLikeId() {
		return this.likeId;
	}

	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}

	public LpUser getLpUser() {
		return this.lpUser;
	}

	public void setLpUser(LpUser lpUser) {
		this.lpUser = lpUser;
	}

	public LpGalary getLpGalary() {
		return this.lpGalary;
	}

	public void setLpGalary(LpGalary lpGalary) {
		this.lpGalary = lpGalary;
	}

	public Timestamp getLikeTime() {
		return this.likeTime;
	}

	public void setLikeTime(Timestamp likeTime) {
		this.likeTime = likeTime;
	}

	public int getLikeStatus() {
		return this.likeStatus;
	}

	public void setLikeStatus(int likeStatus) {
		this.likeStatus = likeStatus;
	}

	public Integer getLikeType() {
		return this.likeType;
	}

	public void setLikeType(Integer likeType) {
		this.likeType = likeType;
	}

	public String getNewsId() {
		return this.newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

}