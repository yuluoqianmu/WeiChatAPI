package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpSubjectLog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpSubjectLog implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private String subjectId;
	private Timestamp accessTime;
	private String machineId;

	// Constructors

	/** default constructor */
	public LpSubjectLog() {
	}

	/** minimal constructor */
	public LpSubjectLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpSubjectLog(String id, String userId, String subjectId,
			Timestamp accessTime, String machineId) {
		this.id = id;
		this.userId = userId;
		this.subjectId = subjectId;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
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