package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpClubLog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpClubLog implements java.io.Serializable {

	// Fields

	private String id;
	private String userId;
	private Integer accessClubCount;
	private Timestamp accessClubTime;
	private String machineId;

	// Constructors

	/** default constructor */
	public LpClubLog() {
	}

	/** minimal constructor */
	public LpClubLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpClubLog(String id, String userId, Integer accessClubCount,
			Timestamp accessClubTime, String machineId) {
		this.id = id;
		this.userId = userId;
		this.accessClubCount = accessClubCount;
		this.accessClubTime = accessClubTime;
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

	public Integer getAccessClubCount() {
		return this.accessClubCount;
	}

	public void setAccessClubCount(Integer accessClubCount) {
		this.accessClubCount = accessClubCount;
	}

	public Timestamp getAccessClubTime() {
		return this.accessClubTime;
	}

	public void setAccessClubTime(Timestamp accessClubTime) {
		this.accessClubTime = accessClubTime;
	}

	public String getMachineId() {
		return this.machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

}