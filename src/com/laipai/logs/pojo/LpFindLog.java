package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpFindLog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpFindLog implements java.io.Serializable {

	// Fields

	private String id;
	private Timestamp accessTime;
	private String userId;
	private String machineId;

	// Constructors

	/** default constructor */
	public LpFindLog() {
	}

	/** minimal constructor */
	public LpFindLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpFindLog(String id, Timestamp accessTime, String userId,
			String machineId) {
		this.id = id;
		this.accessTime = accessTime;
		this.userId = userId;
		this.machineId = machineId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getAccessTime() {
		return this.accessTime;
	}

	public void setAccessTime(Timestamp accessTime) {
		this.accessTime = accessTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMachineId() {
		return this.machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

}