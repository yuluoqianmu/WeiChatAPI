package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpGalarySharelog entity. @author MyEclipse Persistence Tools
 */
@Entity 
public class LpGalarySharelog implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private String galaryId;
	private Timestamp shareTime;
	private String styleId;

	// Constructors

	/** default constructor */
	public LpGalarySharelog() {
	}

	/** minimal constructor */
	public LpGalarySharelog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpGalarySharelog(String id, String userId, String galaryId,
			Timestamp shareTime, String styleId) {
		this.id = id;
		this.userId = userId;
		this.galaryId = galaryId;
		this.shareTime = shareTime;
		this.styleId = styleId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public Timestamp getShareTime() {
		return this.shareTime;
	}

	public void setShareTime(Timestamp shareTime) {
		this.shareTime = shareTime;
	}

	public String getStyleId() {
		return this.styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

}