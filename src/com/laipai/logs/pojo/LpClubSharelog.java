package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpClubSharelog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpClubSharelog implements java.io.Serializable {

	// Fields

	private String id;
	private String shareArticleId;
	private String userId;
	private Timestamp shareTime;

	// Constructors

	/** default constructor */
	public LpClubSharelog() {
	}

	/** minimal constructor */
	public LpClubSharelog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpClubSharelog(String id, String shareArticleId, String userId,
			Timestamp shareTime) {
		this.id = id;
		this.shareArticleId = shareArticleId;
		this.userId = userId;
		this.shareTime = shareTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShareArticleId() {
		return this.shareArticleId;
	}

	public void setShareArticleId(String shareArticleId) {
		this.shareArticleId = shareArticleId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getShareTime() {
		return this.shareTime;
	}

	public void setShareTime(Timestamp shareTime) {
		this.shareTime = shareTime;
	}

}