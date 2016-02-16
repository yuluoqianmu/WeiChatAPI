package com.laipai.userManInfo.dto;

import java.sql.Timestamp;

import javax.persistence.Entity;
@Entity
public class FeedBackBean implements java.io.Serializable {
	private String feedId;
	private String userName;
	private String userQq;
	private String userContent;
	private Timestamp feedTime;
	public String getFeedId() {
		return feedId;
	}
	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserQq() {
		return userQq;
	}
	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}
	public String getUserContent() {
		return userContent;
	}
	public void setUserContent(String userContent) {
		this.userContent = userContent;
	}
	public Timestamp getFeedTime() {
		return feedTime;
	}
	public void setFeedTime(Timestamp feedTime) {
		this.feedTime = feedTime;
	}
	

}
