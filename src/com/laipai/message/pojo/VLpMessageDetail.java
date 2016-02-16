package com.laipai.message.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * VLpMessageDetailId entity. @author MyEclipse Persistence Tools
 */
@Entity
public class VLpMessageDetail implements java.io.Serializable {

	// Fields
	private String mesDetailId;
	private String messageTitle;
	private Timestamp createTime;
	private Integer messageSendPerson;
	private Integer messageStatus;
	private String nickName;
	private String userId;
	private Integer messageType;
	private String messageContent;

	// Constructors

	/** default constructor */
	public VLpMessageDetail() {
	}

	public String getMesDetailId() {
		return mesDetailId;
	}

	public void setMesDetailId(String mesDetailId) {
		this.mesDetailId = mesDetailId;
	}

	public String getMessageTitle() {
		return this.messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getMessageSendPerson() {
		return this.messageSendPerson;
	}

	public void setMessageSendPerson(Integer messageSendPerson) {
		this.messageSendPerson = messageSendPerson;
	}

	public Integer getMessageStatus() {
		return this.messageStatus;
	}

	public void setMessageStatus(Integer messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	

}