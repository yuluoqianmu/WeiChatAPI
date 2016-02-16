package com.laipai.logs.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpCamermanLog entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpCamermanLog implements java.io.Serializable {

	// Fields

	private String id;
	private String camermanId;
	private String vistorId;
	private Timestamp vistedTime;
	private String machineId;
	private Integer enterType;

	// Constructors

	/** default constructor */
	public LpCamermanLog() {
	}

	/** minimal constructor */
	public LpCamermanLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public LpCamermanLog(String id, String camermanId, String vistorId,
			Timestamp vistedTime, String machineId, Integer enterType) {
		this.id = id;
		this.camermanId = camermanId;
		this.vistorId = vistorId;
		this.vistedTime = vistedTime;
		this.machineId = machineId;
		this.enterType = enterType;
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

	public String getVistorId() {
		return this.vistorId;
	}

	public void setVistorId(String vistorId) {
		this.vistorId = vistorId;
	}

	public Timestamp getVistedTime() {
		return this.vistedTime;
	}

	public void setVistedTime(Timestamp vistedTime) {
		this.vistedTime = vistedTime;
	}

	public String getMachineId() {
		return this.machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public Integer getEnterType() {
		return this.enterType;
	}

	public void setEnterType(Integer enterType) {
		this.enterType = enterType;
	}

}