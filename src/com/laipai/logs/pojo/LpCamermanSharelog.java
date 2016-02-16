package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpCamermanSharelog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpCamermanSharelog implements java.io.Serializable {

	// Fields

	private String id;
	private String camermanId;
	private String userId;
	private Timestamp shareTime;
	private Integer shareType;

	// Constructors

	/** default constructor */
	public LpCamermanSharelog() {
	}

	/** minimal constructor */
	public LpCamermanSharelog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpCamermanSharelog(String id, String camermanId, String userId,
			Timestamp shareTime, Integer shareType) {
		this.id = id;
		this.camermanId = camermanId;
		this.userId = userId;
		this.shareTime = shareTime;
		this.shareType = shareType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCamermanId() {
		return this.camermanId;
	}

	public void setCamermanId(String camermanId) {
		this.camermanId = camermanId;
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

	public Integer getShareType() {
		return this.shareType;
	}

	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}

}