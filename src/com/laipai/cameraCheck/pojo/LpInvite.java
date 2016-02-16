package com.laipai.cameraCheck.pojo;

import java.sql.Timestamp;

import com.laipai.userManInfo.pojo.LpUser;

/**
 * LpInvite entity. @author MyEclipse Persistence Tools
 */

public class LpInvite implements java.io.Serializable {

	// Fields

	private String inviteId;
	private LpUser lpUser;
	private String inviteCode;
	private Integer isSend;
	private Integer inviteStatus;
	private Timestamp createtime;
	private Timestamp sendtime;

	// Constructors

	/** default constructor */
	public LpInvite() {
	}

	/** minimal constructor */
	public LpInvite(String inviteId) {
		this.inviteId = inviteId;
	}

	/** full constructor */
	public LpInvite(String inviteId, LpUser lpUser, String inviteCode,
			Integer isSend, Integer inviteStatus, Timestamp createtime,
			Timestamp sendtime) {
		this.inviteId = inviteId;
		this.lpUser = lpUser;
		this.inviteCode = inviteCode;
		this.isSend = isSend;
		this.inviteStatus = inviteStatus;
		this.createtime = createtime;
		this.sendtime = sendtime;
	}

	// Property accessors

	public String getInviteId() {
		return this.inviteId;
	}

	public void setInviteId(String inviteId) {
		this.inviteId = inviteId;
	}

	public LpUser getLpUser() {
		return this.lpUser;
	}

	public void setLpUser(LpUser lpUser) {
		this.lpUser = lpUser;
	}

	public String getInviteCode() {
		return this.inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Integer getIsSend() {
		return this.isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public Integer getInviteStatus() {
		return this.inviteStatus;
	}

	public void setInviteStatus(Integer inviteStatus) {
		this.inviteStatus = inviteStatus;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Timestamp getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

}