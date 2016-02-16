package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpClubArticlelog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpClubArticlelog implements java.io.Serializable {

	// Fields

	private String id;
	private String articleId;
	private String userId;
	private Timestamp accessTime;
	private String machineId;

	// Constructors

	/** default constructor */
	public LpClubArticlelog() {
	}

	/** minimal constructor */
	public LpClubArticlelog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpClubArticlelog(String id, String articleId, String userId,
			Timestamp accessTime, String machineId) {
		this.id = id;
		this.articleId = articleId;
		this.userId = userId;
		this.accessTime = accessTime;
		this.machineId = machineId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArticleId() {
		return this.articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getAccessTime() {
		return this.accessTime;
	}

	public void setAccessTime(Timestamp accessTime) {
		this.accessTime = accessTime;
	}

	public String getMachineId() {
		return this.machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

}