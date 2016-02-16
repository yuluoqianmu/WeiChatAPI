package com.laipai.userManInfo.pojo;

import java.sql.Timestamp;

/**
 * LpFeedbackViewId entity. @author MyEclipse Persistence Tools
 */

public class LpFeedbackViewId implements java.io.Serializable {

	// Fields

	private String feedId;
	private String userId;
	private String userQq;
	private String userContent;
	private Timestamp feedTime;
	private String userName;

	// Constructors

	/** default constructor */
	public LpFeedbackViewId() {
	}

	/** minimal constructor */
	public LpFeedbackViewId(String feedId) {
		this.feedId = feedId;
	}

	/** full constructor */
	public LpFeedbackViewId(String feedId, String userId, String userQq,
			String userContent, Timestamp feedTime, String userName) {
		this.feedId = feedId;
		this.userId = userId;
		this.userQq = userQq;
		this.userContent = userContent;
		this.feedTime = feedTime;
		this.userName = userName;
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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LpFeedbackViewId))
			return false;
		LpFeedbackViewId castOther = (LpFeedbackViewId) other;

		return ((this.getFeedId() == castOther.getFeedId()) || (this
				.getFeedId() != null && castOther.getFeedId() != null && this
				.getFeedId().equals(castOther.getFeedId())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())))
				&& ((this.getUserQq() == castOther.getUserQq()) || (this
						.getUserQq() != null && castOther.getUserQq() != null && this
						.getUserQq().equals(castOther.getUserQq())))
				&& ((this.getUserContent() == castOther.getUserContent()) || (this
						.getUserContent() != null
						&& castOther.getUserContent() != null && this
						.getUserContent().equals(castOther.getUserContent())))
				&& ((this.getFeedTime() == castOther.getFeedTime()) || (this
						.getFeedTime() != null
						&& castOther.getFeedTime() != null && this
						.getFeedTime().equals(castOther.getFeedTime())))
				&& ((this.getUserName() == castOther.getUserName()) || (this
						.getUserName() != null
						&& castOther.getUserName() != null && this
						.getUserName().equals(castOther.getUserName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFeedId() == null ? 0 : this.getFeedId().hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getUserQq() == null ? 0 : this.getUserQq().hashCode());
		result = 37
				* result
				+ (getUserContent() == null ? 0 : this.getUserContent()
						.hashCode());
		result = 37 * result
				+ (getFeedTime() == null ? 0 : this.getFeedTime().hashCode());
		result = 37 * result
				+ (getUserName() == null ? 0 : this.getUserName().hashCode());
		return result;
	}

}