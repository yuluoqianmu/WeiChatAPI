package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpGalarydetailShowlog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpGalarydetailShowlog implements java.io.Serializable {

	// Fields

	private String id;
	private String galaryDetailId;
	private Timestamp showTime;
	private String userId;
	private String machineId;

	// Constructors

	/** default constructor */
	public LpGalarydetailShowlog() {
	}

	/** minimal constructor */
	public LpGalarydetailShowlog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpGalarydetailShowlog(String id, String galaryDetailId,
			Timestamp showTime, String userId, String machineId) {
		this.id = id;
		this.galaryDetailId = galaryDetailId;
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

	public String getGalaryDetailId() {
		return this.galaryDetailId;
	}

	public void setGalaryDetailId(String galaryDetailId) {
		this.galaryDetailId = galaryDetailId;
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