package com.laipai.userManInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpFeedback entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpFeedback implements java.io.Serializable {

	// Fields

	private String feedId;
	private String userId;
	private String userQq;
	private String userContent;
	private Timestamp feedTime;

	// Constructors

	/** default constructor */
	public LpFeedback() {
	}

	/** minimal constructor */
	public LpFeedback(String feedId) {
		this.feedId = feedId;
	}

	/** full constructor */
	public LpFeedback(String feedId, String userId, String userQq,
			String userContent, Timestamp feedTime) {
		this.feedId = feedId;
		this.userId = userId;
		this.userQq = userQq;
		this.userContent = userContent;
		this.feedTime = feedTime;
	}

	// Property accessors

	public String getFeedId() {
		return this.feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserQq() {
		return this.userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	public String getUserContent() {
		return this.userContent;
	}

	public void setUserContent(String userContent) {
		this.userContent = userContent;
	}

	public Timestamp getFeedTime() {
		return this.feedTime;
	}

	public void setFeedTime(Timestamp feedTime) {
		this.feedTime = feedTime;
	}

}