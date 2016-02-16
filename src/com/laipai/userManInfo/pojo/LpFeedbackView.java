package com.laipai.userManInfo.pojo;

/**
 * LpFeedbackView entity. @author MyEclipse Persistence Tools
 */

public class LpFeedbackView implements java.io.Serializable {

	// Fields

	private LpFeedbackViewId id;

	// Constructors

	/** default constructor */
	public LpFeedbackView() {
	}

	/** full constructor */
	public LpFeedbackView(LpFeedbackViewId id) {
		this.id = id;
	}

	// Property accessors

	public LpFeedbackViewId getId() {
		return this.id;
	}

	public void setId(LpFeedbackViewId id) {
		this.id = id;
	}

}