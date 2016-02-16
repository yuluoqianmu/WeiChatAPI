package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpGalarydetailLog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpGalarydetailLog implements java.io.Serializable {

	// Fields

	private String id;
	private String galaryDetailId;
	private Integer enterType;
	private String userId;
	private Timestamp accessTime;
	private String machineId;

	// Constructors

	/** default constructor */
	public LpGalarydetailLog() {
	}

	/** minimal constructor */
	public LpGalarydetailLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpGalarydetailLog(String id, String galaryDetailId,
			Integer enterType, String userId, Timestamp accessTime,
			String machineId) {
		this.id = id;
		this.galaryDetailId = galaryDetailId;
		this.enterType = enterType;
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

	public String getGalaryDetailId() {
		return this.galaryDetailId;
	}

	public void setGalaryDetailId(String galaryDetailId) {
		this.galaryDetailId = galaryDetailId;
	}

	public Integer getEnterType() {
		return this.enterType;
	}

	public void setEnterType(Integer enterType) {
		this.enterType = enterType;
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