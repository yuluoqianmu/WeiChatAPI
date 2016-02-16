package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpGalaryShowlog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpGalaryShowlog implements java.io.Serializable {

	// Fields

	private String id;
	private String detailId;
	private Timestamp showTime;
	private String userId;
	private String machineId;

	// Constructors

	/** default constructor */
	public LpGalaryShowlog() {
	}

	/** minimal constructor */
	public LpGalaryShowlog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpGalaryShowlog(String id, String detailId, Timestamp showTime,
			String userId, String machineId) {
		this.id = id;
		this.detailId = detailId;
		this.showTime = showTime;
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

	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public Timestamp getShowTime() {
		return this.showTime;
	}

	public void setShowTime(Timestamp showTime) {
		this.showTime = showTime;
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