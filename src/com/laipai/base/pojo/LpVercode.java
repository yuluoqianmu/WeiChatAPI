package com.laipai.base.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpVercode entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpVercode implements java.io.Serializable {

	// Fields

	private String vercodeId;
	private String vercode;
	private String mobileTo;
	private String sendMsg;
	private Timestamp sendTime;
	private String remark;
	private Integer vercodeType;
	private Integer status;

	// Constructors

	/** default constructor */
	public LpVercode() {
	}

	public String getVercodeId() {
		return this.vercodeId;
	}

	public void setVercodeId(String vercodeId) {
		this.vercodeId = vercodeId;
	}

	public String getVercode() {
		return this.vercode;
	}

	public void setVercode(String vercode) {
		this.vercode = vercode;
	}

	public String getMobileTo() {
		return this.mobileTo;
	}

	public void setMobileTo(String mobileTo) {
		this.mobileTo = mobileTo;
	}
	public String getSendMsg() {
		return this.sendMsg;
	}

	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}

	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVercodeType() {
		return this.vercodeType;
	}

	public void setVercodeType(Integer vercodeType) {
		this.vercodeType = vercodeType;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}