package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpGalaryLog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpGalaryLog implements java.io.Serializable {

	// Fields

	private String id;
	private String galaryId;
	private String userId;
	private Timestamp accessTime;
	private String styleId;
	private String machineId;

	// Constructors

	/** default constructor */
	public LpGalaryLog() {
	}

	/** minimal constructor */
	public LpGalaryLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpGalaryLog(String id, String galaryId, String userId,
			Timestamp accessTime, String styleId, String machineId) {
		this.id = id;
		this.galaryId = galaryId;
		this.userId = userId;
		this.accessTime = accessTime;
		this.styleId = styleId;
		this.machineId = machineId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
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

	public String getStyleId() {
		return this.styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getMachineId() {
		return this.machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

}